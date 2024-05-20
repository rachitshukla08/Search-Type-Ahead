package com.rachit.searchsuggestion.manager;

import com.rachit.searchsuggestion.data.Constants;
import com.rachit.searchsuggestion.data.Suggestion;
import com.rachit.searchsuggestion.trie.SuggestionsCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SuggestionsManager {

    private final SuggestionsCache suggestionsCache;

    @Autowired
    public SuggestionsManager(SuggestionsCache suggestionsCache) {
        this.suggestionsCache = suggestionsCache;
    }

    public List<Suggestion> getSuggestions(String query){
        if(query.length()> Constants.MAX_QUERY_SIZE){
            throw new RuntimeException("Query out of limit");
        }
        query = query.toLowerCase();
        return this.suggestionsCache.getCachedSuggestions(query);
    }
}
