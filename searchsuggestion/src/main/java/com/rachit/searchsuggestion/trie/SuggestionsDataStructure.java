package com.rachit.searchsuggestion.trie;

import com.rachit.searchsuggestion.data.Suggestion;

import java.util.List;

public interface SuggestionsDataStructure {
    void init(int maxSuggestions);
    List<Suggestion> getTopSuggestions(String query);
    void reload();
}
