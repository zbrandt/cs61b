package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import ngrams.NGramMap;
import ngrams.TimeSeries;

import java.util.List;

public class HistoryTextHandler extends NgordnetQueryHandler {
    private NGramMap map;

    public HistoryTextHandler(NGramMap map) {
        this.map = map;
    }

    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();

        String response = "";
        for (String word : words) {
            response += word + ": {";
            TimeSeries ts = this.map.weightHistory(word, startYear, endYear);

            List<Integer> years = ts.years();

            List<Double> data = ts.data();
            for (int i = 0; i < years.size(); i++) {
                if (i != years.size() - 1) {
                    response += years.get(i) + "=" + data.get(i) + ", ";
                } else {
                    response += years.get(i) + "=" + data.get(i) + "}";
                }
            }
            response += "\n";
        }
        return response;
    }
}
