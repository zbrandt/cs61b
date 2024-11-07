package main;

import browser.NgordnetQueryHandler;
import ngrams.NGramMap;


public class AutograderBuddy {
    /** Returns a HyponymHandler */
    public static NgordnetQueryHandler getHyponymsHandler(String wordFile, String countFile,
                                                          String synsetFile, String hyponymFile) {
        NGramMap ngrams = new NGramMap(wordFile, countFile);
        WordNet wordnet = new WordNet(synsetFile, hyponymFile, ngrams);
        return new HyponymsHandler(wordnet);
    }

}
