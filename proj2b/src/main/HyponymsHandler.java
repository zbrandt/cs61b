package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HyponymsHandler extends NgordnetQueryHandler {
    private WordNet wordnet;

    public HyponymsHandler(WordNet wordnet) {
        this.wordnet = wordnet;
    }

    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();
        int k = q.k();
        List<String> hyponyms = this.wordnet.hyponyms(words, k, startYear, endYear);
        return hyponyms.toString();
    }
}
