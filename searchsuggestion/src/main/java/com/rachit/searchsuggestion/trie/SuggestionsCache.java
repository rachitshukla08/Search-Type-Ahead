package com.rachit.searchsuggestion.trie;

import com.rachit.searchsuggestion.data.Suggestion;

import java.util.List;

public interface SuggestionsCache {
    List<Suggestion> getCachedSuggestions(String query);
}
