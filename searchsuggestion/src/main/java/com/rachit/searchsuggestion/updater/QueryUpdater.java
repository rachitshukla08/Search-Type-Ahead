package com.rachit.searchsuggestion.updater;

import com.rachit.searchsuggestion.db.FrequencyCount;
import com.rachit.searchsuggestion.db.FrequencyCountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QueryUpdater {
    private final FrequencyCountRepository repository;

    @Autowired
    public QueryUpdater(FrequencyCountRepository repository) {
        this.repository = repository;
    }

    public synchronized void updateQuery(String query) {
        FrequencyCount fc = this.repository.findByQuery(query);
        if(fc != null){
            FrequencyCount updated = new FrequencyCount();
            updated.setQuery(query);
            updated.setFrequency(fc.getFrequency()+1);
            this.repository.save(updated);
            this.repository.delete(fc);
        } else {
            FrequencyCount newFrequencyCount = new FrequencyCount();
            newFrequencyCount.setQuery(query);
            newFrequencyCount.setFrequency(1);
            this.repository.save(newFrequencyCount);
        }
    }

}
