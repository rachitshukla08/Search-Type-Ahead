package com.rachit.searchsuggestion.data;

public class Suggestion {
    private final String suggestedText;
    private final int frequency;

    public Suggestion(String suggestedText, int frequency) {
        this.suggestedText = suggestedText;
        this.frequency = frequency;
    }

    public String getSuggestedText() {
        return suggestedText;
    }

    public int getFrequency() {
        return frequency;
    }
}
