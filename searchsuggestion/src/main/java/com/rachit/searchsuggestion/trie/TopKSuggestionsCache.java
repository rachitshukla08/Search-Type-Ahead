package com.rachit.searchsuggestion.trie;

import com.rachit.searchsuggestion.data.Suggestion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TopKSuggestionsCache implements SuggestionsCache {
    private final SuggestionsDataStructure dataStructure;

    @Autowired
    public TopKSuggestionsCache(SuggestionsDataStructure suggestionsDataStructure) {
        this.dataStructure = suggestionsDataStructure;
    }

    @Override
    public List<Suggestion> getCachedSuggestions(String query) {
        return this.dataStructure.getTopSuggestions(query);
    }
}
