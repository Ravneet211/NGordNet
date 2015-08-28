package ngordnet;
import java.util.Map;
import java.util.HashMap;
import java.util.Collection;
import edu.princeton.cs.introcs.In;
public class NGramMap {
    /** Constructs an NGramMap from WORDSFILENAME and COUNTSFILENAME. */
    Map<String, TimeSeries<Integer>> wordsinayear = new HashMap<String, TimeSeries<Integer>>();
    Map<Integer, YearlyRecord> recordedyears = new HashMap<Integer, YearlyRecord>();
    TimeSeries<Long> totalcount = new TimeSeries<Long>();
    public NGramMap(String wordsFilename, String countsFilename) {
        In in1 = new In(wordsFilename);
        HashMap<String, Integer> y = new HashMap<String, Integer>();
        String s;
        Integer year, count;
        String[] tokens;
        TimeSeries<Integer> t = new TimeSeries<Integer>();
        while (in1.hasNextLine()) {
            s = in1.readLine();
            tokens = s.split("[\t]+");
            year = Integer.parseInt(tokens[1]);
            count = Integer.parseInt(tokens[2]);
           
            if (wordsinayear.containsKey(tokens[0])) {
                wordsinayear.get(tokens[0]).put(year, count);
            } else {
                t.clear();
                t.put(year, count);
                wordsinayear.put(tokens[0], new TimeSeries<Integer>(t));
            }
            if (recordedyears.containsKey(year)) {
                recordedyears.get(year).put(tokens[0], count);
            } else {   
                y.clear();
                y.put(tokens[0], count);
                recordedyears.put(year, new YearlyRecord(y));
            }
        }
        In in2 = new In(countsFilename);
        while (in2.hasNextLine()) {
            s = in2.readLine();
            tokens = s.split("[,]+");
            totalcount.put(Integer.parseInt(tokens[0]), Long.parseLong(tokens[1]));
        }
    }

    
    /** Returns the absolute count of WORD in the given YEAR. If the word
      * did not appear in the given year, return 0. */
    public int countInYear(String word, int year) {   
        if (wordsinayear.get(word) == null || wordsinayear.get(word).get(year) == null)
        {
            return 0;
        }
        return (Integer) wordsinayear.get(word).get(year);
    }

    /** Returns a defensive copy of the YearlyRecord of YEAR. */
    public YearlyRecord getRecord(int year) {
        HashMap<String, Integer> temp = new HashMap<String, Integer>();
        for (String word : recordedyears.get(year).words()) {
            temp.put(word, recordedyears.get(year).count(word));
        } 
        return new YearlyRecord(temp);
    }

    /** Returns the total number of words recorded in all volumes. */
    public TimeSeries<Long> totalCountHistory() {
        return totalcount;
    }

    /** Provides the history of WORD between STARTYEAR and ENDYEAR. */
    public TimeSeries<Integer> countHistory(String word, int startYear, int endYear) {
        TimeSeries<Integer> specificcounthistory = new TimeSeries<Integer>();
        for (int i = startYear; i <= endYear; i++) {   
            if (countInYear(word, i) != 0) {
                specificcounthistory.put(i, countInYear(word, i));
            }
        }
        return specificcounthistory;
    }

    /** Provides a defensive copy of the history of WORD. */
    public TimeSeries<Integer> countHistory(String word) {
        return wordsinayear.get(word);
    }

    /** Provides the relative frequency of WORD between STARTYEAR and ENDYEAR. */
    public TimeSeries<Double> weightHistory(String word, int startYear, int endYear) { 
        return countHistory(word, startYear, endYear).dividedBy(totalcount);
    }

    /** Provides the relative frequency of WORD. */
    public TimeSeries<Double> weightHistory(String word) {
        return countHistory(word).dividedBy(totalcount);
    }

    /** Provides the summed relative frequency of all WORDS between
      * STARTYEAR and ENDYEAR. If a word does not exist, ignore it rather
      * than throwing an exception. */
    public TimeSeries<Double> summedWeightHistory(Collection<String> words, 
                              int startYear, int endYear) {
        return new TimeSeries<Double>(summedWeightHistory(words), startYear, endYear);
    }

    /** Returns the summed relative frequency of all WORDS. */
    public TimeSeries<Double> summedWeightHistory(Collection<String> words) {
        TimeSeries<Double> sum = new TimeSeries<Double>();
        for (String word : words) {   
            if (countHistory(word) != null) {
                sum = sum.plus(weightHistory(word));
            }
        }    
        return sum;
    }
     /** Provides processed history of all words between STARTYEAR and ENDYEAR as processed
      * by YRP. */
    public TimeSeries<Double> processedHistory(int startYear, int endYear,
                                               YearlyRecordProcessor yrp) {
        return new TimeSeries(processedHistory(yrp), startYear, endYear);
    }                                    

    /** Provides processed history of all words ever as processed by YRP. */
    public TimeSeries<Double> processedHistory(YearlyRecordProcessor yrp) {   
        TimeSeries<Double> t = new TimeSeries<Double>();
        for (Integer year : recordedyears.keySet()) {
            t.put(year, yrp.process(recordedyears.get(year)));
        }
        return t;
    }
}


