package com.rachit.searchsuggestion.trie;

import com.rachit.searchsuggestion.data.Suggestion;

import java.util.ArrayList;
import java.util.List;

public class TrieNode {
    private boolean isEOW;
    private final List<TrieNode> pointers;


    private List<Suggestion> topSuggestions;

    public TrieNode() {
        this.isEOW = false;
        this.pointers = new ArrayList<>();
        for(int i=0; i<26; i++){
            this.pointers.add(null);
        }
        this.topSuggestions = new ArrayList<>();
    }

    public boolean isEOW() {
        return isEOW;
    }

    public void setEOW(boolean isEOW){
        this.isEOW = isEOW;
    }

    public List<TrieNode> getPointers() {
        return pointers;
    }

    public List<Suggestion> getTopSuggestions() {
        return topSuggestions;
    }

    public void setTopSuggestions(List<Suggestion> topSuggestions) {
        this.topSuggestions = topSuggestions;
    }

}
