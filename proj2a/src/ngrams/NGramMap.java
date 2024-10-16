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
 * An NGramMap stores pertinent data from a "words file" and a "counts
 * file". It is not a map in the strict sense, but it does provide additional
 * functionality.
 *
 * @author Josh Hug
 */
public class NGramMap {
    HashMap<String, TimeSeries> words;
    TimeSeries counts;

    /**
     * Constructs an NGramMap from WORDSFILENAME and COUNTSFILENAME.
     */
    public NGramMap(String wordsFilename, String countsFilename) {
        this.words = new HashMap<>();
        this.counts = new TimeSeries();
        In wordsFile = new In(wordsFilename);
        In countsFile = new In(countsFilename);

        String word = "";
        while (wordsFile.hasNextLine()) {
            String nextLine = wordsFile.readLine();
            String[] splitLine = nextLine.split("\t");
            if (word.isEmpty()) {
                word = splitLine[0];
                this.words.put(word, new TimeSeries());
            }
            if (!word.equals(splitLine[0])) {
                word = splitLine[0];
                this.words.put(word, new TimeSeries());
            }
            int year = Integer.parseInt(splitLine[1]);
            double count = Double.parseDouble(splitLine[2]);
            this.words.get(word).put(year, count);
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
     * words, changes made to the object returned by this function should not also affect the
     * NGramMap. This is also known as a "defensive copy". If the word is not in the data files,
     * returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word, int startYear, int endYear) {
        if (!this.words.containsKey(word)) {
            return new TimeSeries();
        } else {
            return new TimeSeries(this.words.get(word), startYear, endYear);
        }
    }

    /**
     * Provides the history of WORD. The returned TimeSeries should be a copy, not a link to this
     * NGramMap's TimeSeries. In other words, changes made to the object returned by this function
     * should not also affect the NGramMap. This is also known as a "defensive copy". If the word
     * is not in the data files, returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word) {
        if (!this.words.containsKey(word)) {
            return new TimeSeries();
        } else {
            TimeSeries copy = new TimeSeries();
            copy.putAll(this.words.get(word));
            return copy;
        }
    }

    /**
     * Returns a defensive copy of the total number of words recorded per year in all volumes.
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
        if (!this.words.containsKey(word)) {
            return new TimeSeries();
        } else {
            TimeSeries numerator = new TimeSeries(this.words.get(word), startYear, endYear);
            TimeSeries denominator = new TimeSeries(this.counts, startYear, endYear);
            return numerator.dividedBy(denominator);
        }
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD compared to all
     * words recorded in that year. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word) {
        if (!this.words.containsKey(word)) {
            return new TimeSeries();
        } else {
            TimeSeries ts = this.words.get(word);
            return ts.dividedBy(this.counts);
        }
    }

    /**
     * Provides the summed relative frequency per year of all words in WORDS between STARTYEAR and
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
     * Returns the summed relative frequency per year of all words in WORDS. If a word does not
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
