package com.rachit.searchsuggestion.updater;

import com.rachit.searchsuggestion.trie.SuggestionsDataStructure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CacheToDBSyncManager implements Runnable{
    private final SuggestionsDataStructure suggestionsDataStructure;

    @Autowired
    public CacheToDBSyncManager(SuggestionsDataStructure suggestionsDataStructure) {
        this.suggestionsDataStructure = suggestionsDataStructure;
    }

    @Override
    public void run() {
        sync();
    }

    public void sync() {
        while (true) {
            try {
                Thread.sleep(30000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Starting to sync");
            this.suggestionsDataStructure.reload();
            System.out.println("Finished sync");
        }
    }
}
