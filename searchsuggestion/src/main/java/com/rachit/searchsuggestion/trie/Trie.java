package com.rachit.searchsuggestion.trie;

import com.rachit.searchsuggestion.data.Constants;
import com.rachit.searchsuggestion.data.Suggestion;
import com.rachit.searchsuggestion.db.FrequencyCount;
import com.rachit.searchsuggestion.db.FrequencyCountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class Trie implements SuggestionsDataStructure{
    private volatile TrieNode root;
    private final FrequencyCountRepository repository;
    private int k = Constants.MAX_SUGGESTIONS_LIMIT;

    @Autowired
    public Trie(FrequencyCountRepository frequencyCountRepository, Optional<Integer> maxSuggestions) {
        this.root = new TrieNode();
        this.repository = frequencyCountRepository;
        init(maxSuggestions.isEmpty() ? k : maxSuggestions.get());
    }

    @Override
    public void init(int maxSuggestions) {
        k = Math.max(k, maxSuggestions);
        constructTrie(root);
    }

    private void constructTrie(TrieNode root) {
        List<FrequencyCount> frequencyCounts =
                this.repository.findAll();
        for (FrequencyCount fc : frequencyCounts) {
            insert(fc.getQuery(), fc.getFrequency(), 0, root);
        }
    }

    private List<Suggestion> insert(String query, int frequency, int idx, TrieNode curr) {
        if(idx == query.length() || idx == Constants.MAX_QUERY_SIZE) {
            Set<Suggestion> suggestions = new HashSet<>(curr.getTopSuggestions());
            suggestions.add(new Suggestion(query, frequency));
            return updateSuggestionsAndGet(suggestions,curr);
        }
        if(curr.getPointers().get(query.charAt(idx)-'a')==null){
            curr.getPointers().set(query.charAt(idx)-'a', new TrieNode());
        }
        TrieNode next = curr.getPointers().get(query.charAt(idx)-'a');
        Set<Suggestion> allSuggestions = new HashSet<>();
        if(idx == query.length()-1){
            next.setEOW(true);
        }
        insert(query, frequency, idx+1, next);
        allSuggestions.addAll(curr.getTopSuggestions());
        for(int i=0;i<26; i++){
            TrieNode node= curr.getPointers().get(i);
            if(node!=null){
                allSuggestions.addAll(node.getTopSuggestions());
            }
        }
        return updateSuggestionsAndGet(allSuggestions, curr);
    }

    private List<Suggestion> updateSuggestionsAndGet(Set<Suggestion> suggestions, TrieNode curr){
        List<Suggestion> allSuggestionsList = new ArrayList<>();
        allSuggestionsList.addAll(suggestions);
        Collections.sort(allSuggestionsList, new Comparator<Suggestion>() {
            @Override
            public int compare(Suggestion o1, Suggestion o2) {
                return o2.getFrequency() - o1.getFrequency();
            }
        });
        List<Suggestion> finalSuggestions = new ArrayList<>();
        for(int i=0; i<Math.min(k, allSuggestionsList.size()); i++){
            finalSuggestions.add(allSuggestionsList.get(i));
        }
        curr.setTopSuggestions(finalSuggestions);
        return curr.getTopSuggestions();
    }

    @Override
    public List<Suggestion> getTopSuggestions(String query) {
        TrieNode curr = root;
        int i =0;
        List<Suggestion> ans = new ArrayList<>();
        while (true){
            curr = curr.getPointers().get(query.charAt(i)-'a');
            if(curr == null)
                return ans;
            i++;
            if(i==query.length())
                return curr.getTopSuggestions();
        }
    }

    @Override
    public void reload() {
        TrieNode temp = new TrieNode();
        constructTrie(temp);
        this.root = temp;
    }
}
