package main;

import edu.princeton.cs.algs4.In;
import ngrams.NGramMap;
import ngrams.TimeSeries;

import java.util.*;

public class WordNet {
    private HashMap<Integer, String> idToNoun;
    private HashMap<String, List<Integer>> nounToIds;
    private Graph graph;
    private NGramMap ngrams;
//    private static final int DEFAULT_STARTYEAR = 1900;
//    private static final int DEFAULT_ENDYEAR = 2020;

    public WordNet(String synsetsFilename, String hyponymsFilename, NGramMap ngrams) {
        this.idToNoun = new HashMap<>();
        this.nounToIds = new HashMap<>();
        this.graph = new Graph();
        this.ngrams = ngrams;
        In synsetsFile = new In(synsetsFilename);
        In hyponymsFile = new In(hyponymsFilename);

        while (synsetsFile.hasNextLine()) {
            String nextLine = synsetsFile.readLine();
            String[] splitLine = nextLine.split(",");
            int id = Integer.parseInt(splitLine[0]);
            this.idToNoun.put(id, splitLine[1]);
            String[] nouns = splitLine[1].split(" ");
            for (String noun : nouns) {
                if (this.nounToIds.get(noun) == null) {
                    List<Integer> ids = new ArrayList<>();
                    ids.add(id);
                    this.nounToIds.put(noun, ids);
                } else {
                    this.nounToIds.get(noun).add(id);
                }
            }
        }

        while (hyponymsFile.hasNextLine()) {
            String nextLine = hyponymsFile.readLine();
            String[] splitLine = nextLine.split(",");
            int hypernym = Integer.parseInt(splitLine[0]);
            for (int i = 1; i < splitLine.length; i += 1) {
                int hyponym = Integer.parseInt(splitLine[i]);
                this.graph.addEdge(hypernym, hyponym);
            }
        }
    }

    public List<String> hyponyms(String hypernym) {
        List<Integer> ids = this.nounToIds.get(hypernym);
        List<String> hyponyms = new ArrayList<>();
        hyponyms.add(hypernym);
        for (Integer id : ids) {
            Paths p = new Paths(this.graph, id);
            for (Integer child : p.descendants()) {
                String[] words = this.idToNoun.get(child).split(" ");
                for (String word : words) {
                    if (!hyponyms.contains(word)) {
                        hyponyms.add(word);
                    }
                }
            }
        }
        Collections.sort(hyponyms);
        return hyponyms;
    }

    public List<String> hyponyms(List<String> hypernyms) {
        List<String> hyponyms = new ArrayList<>();
        List<String>[] combined = new ArrayList[hypernyms.size()];
        for (int i = 0; i < combined.length; i += 1) {
            combined[i] = hyponyms(hypernyms.get(i));
        }
        for (String word : combined[0]) {
            hyponyms.add(word);
            for (int i = 1; i < combined.length; i += 1) {
                if (!combined[i].contains(word)) {
                    hyponyms.remove(word);
                }
            }
        }
        Collections.sort(hyponyms);
        return hyponyms;
    }

//    public List<String> hyponyms(List<String> hypernyms, int k) {
//        return hyponyms(hypernyms, k, DEFAULT_STARTYEAR, DEFAULT_ENDYEAR);
//    }

    public List<String> hyponyms(List<String> hypernyms, int k, int startYear, int endYear) {
        HashMap<String, Double> wordToCount = new HashMap<>();
        List<String> allHyponyms = hyponyms(hypernyms);
        List<String> hyponyms = new ArrayList<>();
        for (String word : allHyponyms) {
            TimeSeries ts = ngrams.countHistory(word, startYear, endYear);
            double count = 0;
            List<Double> data = ts.data();
            for (Double datum : data) {
                count += datum;
            }
            wordToCount.put(word, count);
        }
        while (hyponyms.size() < k) {
            String max = "";
            for (String word : allHyponyms) {
                if (max.isEmpty()) {
                    max = word;
                } else if (wordToCount.get(max) < wordToCount.get(word)) {
                    max = word;
                }
            }
            hyponyms.add(max);
            allHyponyms.remove(max);
        }
        Collections.sort(hyponyms);
        return hyponyms;
    }
}
