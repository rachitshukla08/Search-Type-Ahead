package com.rachit.searchsuggestion.controllers;

import com.rachit.searchsuggestion.manager.SuggestionsManager;
import com.rachit.searchsuggestion.updater.QueryUpdater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/search")
public class TypeAheadController {


    private final SuggestionsManager suggestionsManager;
    private final QueryUpdater queryUpdater;

    @Autowired
    public TypeAheadController(SuggestionsManager suggestionsManager, QueryUpdater queryUpdater) {
        this.suggestionsManager = suggestionsManager;
        this.queryUpdater = queryUpdater;
    }

    @RequestMapping(path = "/suggestions", method = RequestMethod.GET)
    public ResponseEntity<?> getSuggestionsAPI(@RequestParam(value = "query") String query) {
        if(query == null || query.isEmpty())
            return new ResponseEntity<>("Invalid query", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(this.suggestionsManager.getSuggestions(query), HttpStatus.OK);
    }

    @RequestMapping(path = "/update", method = RequestMethod.POST)
    public ResponseEntity<?> updateQueries(@RequestParam String query) {
        if(query == null || query.isEmpty())
            return new ResponseEntity<>("Invalid query", HttpStatus.BAD_REQUEST);
        this.queryUpdater.updateQuery(query);
        return new ResponseEntity<>("Update successful",HttpStatus.OK);
    }
}
