package ngrams;

import edu.princeton.cs.algs4.In;

import java.util.Collection;
import java.util.HashMap;

//import static ngrams.TimeSeries.MAX_YEAR;
//import static ngrams.TimeSeries.MIN_YEAR;

/**
 * An object that provides utility methods for making queries on the
 * Google NGrams dataset (or a subset thereof).
 *
 * An NGramMap stores pertinent data from a "map file" and a "counts
 * file". It is not a map in the strict sense, but it does provide additional
 * functionality.
 *
 * @author Josh Hug
 */
public class NGramMap {
    HashMap<String, TimeSeries> map;
    TimeSeries counts;

    /**
     * Constructs an NGramMap from WORDSFILENAME and COUNTSFILENAME.
     */
    public NGramMap(String wordsFilename, String countsFilename) {
        this.map = new HashMap<>();
        this.counts = new TimeSeries();
        In wordsFile = new In(wordsFilename);
        In countsFile = new In(countsFilename);

        String word = "";
        while (wordsFile.hasNextLine()) {
            String nextLine = wordsFile.readLine();
            String[] splitLine = nextLine.split("\t");
            if (word.isEmpty()) {
                word = splitLine[0];
                this.map.put(word, new TimeSeries());
            }
            if (!word.equals(splitLine[0])) {
                word = splitLine[0];
                this.map.put(word, new TimeSeries());
            }
            int year = Integer.parseInt(splitLine[1]);
            double count = Double.parseDouble(splitLine[2]);
            this.map.get(word).put(year, count);
        }

        while (countsFile.hasNextLine()) {
            String nextLine = countsFile.readLine();
            String[] splitLine = nextLine.split(",");
            int year = Integer.parseInt(splitLine[0]);
            double count = Double.parseDouble(splitLine[1]);
            this.counts.put(year, count);
        }

    }

    /**
     * Provides the history of WORD between STARTYEAR and ENDYEAR, inclusive of both ends. The
     * returned TimeSeries should be a copy, not a link to this NGramMap's TimeSeries. In other
     * map, changes made to the object returned by this function should not also affect the
     * NGramMap. This is also known as a "defensive copy". If the word is not in the data files,
     * returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word, int startYear, int endYear) {
        if (!this.map.containsKey(word)) {
            return new TimeSeries();
        } else {
            return new TimeSeries(this.map.get(word), startYear, endYear);
        }
    }

    /**
     * Provides the history of WORD. The returned TimeSeries should be a copy, not a link to this
     * NGramMap's TimeSeries. In other map, changes made to the object returned by this function
     * should not also affect the NGramMap. This is also known as a "defensive copy". If the word
     * is not in the data files, returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word) {
        if (!this.map.containsKey(word)) {
            return new TimeSeries();
        } else {
            TimeSeries copy = new TimeSeries();
            copy.putAll(this.map.get(word));
            return copy;
        }
    }

    /**
     * Returns a defensive copy of the total number of map recorded per year in all volumes.
     */
    public TimeSeries totalCountHistory() {
        TimeSeries copy = new TimeSeries();
        copy.putAll(this.counts);
        return copy;
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD between STARTYEAR
     * and ENDYEAR, inclusive of both ends. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word, int startYear, int endYear) {
        if (!this.map.containsKey(word)) {
            return new TimeSeries();
        } else {
            TimeSeries numerator = this.map.get(word);
            TimeSeries denominator = this.counts;
            TimeSeries quotient = numerator.dividedBy(denominator);
            return new TimeSeries(quotient, startYear, endYear);
        }
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD compared to all
     * map recorded in that year. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word) {
        if (!this.map.containsKey(word)) {
            return new TimeSeries();
        } else {
            TimeSeries ts = this.map.get(word);
            return ts.dividedBy(this.counts);
        }
    }

    /**
     * Provides the summed relative frequency per year of all map in WORDS between STARTYEAR and
     * ENDYEAR, inclusive of both ends. If a word does not exist in this time frame, ignore it
     * rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words, int startYear, int endYear) {
        TimeSeries ts = new TimeSeries();
        for (String word : words) {
            TimeSeries frequency = weightHistory(word, startYear, endYear);
            ts.plus(frequency);
        }
        return ts;
    }

    /**
     * Returns the summed relative frequency per year of all map in WORDS. If a word does not
     * exist in this time frame, ignore it rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words) {
        TimeSeries ts = new TimeSeries();
        for (String word : words) {
            TimeSeries frequency = weightHistory(word);
            ts.plus(frequency);
        }
        return ts;
    }
}
