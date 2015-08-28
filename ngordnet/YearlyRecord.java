package ngordnet;
import java.util.TreeMap;
import java.util.HashMap;
import java.util.Collection;
import java.util.Set;
import java.util.HashSet;
import java.util.ArrayList;
public class YearlyRecord {
    /** Creates a new empty YearlyRecord. */
    private TreeMap<Number, Set<String>> counttowords;
    private TreeMap<String, Number> wordstocount;
    private TreeMap<String, Number> rankMap;
    private boolean cached;
    private int size;
    public YearlyRecord() {
        counttowords = new TreeMap<Number, Set<String>>();
        wordstocount = new TreeMap<String, Number>();
        rankMap = new TreeMap<String, Number>();
        cached = false;
        size = 0;
    } 
    /** Creates a YearlyRecord using the given data. */
    public YearlyRecord(HashMap<String, Integer> otherCountMap) {
        wordstocount = new TreeMap<String, Number>(otherCountMap);
        counttowords = new TreeMap<Number, Set<String>>();
        rankMap = new TreeMap<String, Number>();
        Number count;
        Set<String> temp = new HashSet<String>();
        for (String word: wordstocount.keySet()) {
            count = wordstocount.get(word);
            if (counttowords.containsKey(count)) {
                counttowords.get(count).add(word);
            } else {   
                temp.add(word);
                counttowords.put(count, new HashSet<String>(temp));
            }
            temp.clear();

        }
        cached = false;
        size = otherCountMap.size();
    }

    /** Returns the number of times WORD appeared in this year. */
    public int count(String word) {
        return (Integer) wordstocount.get(word);
    }
    /** Records that WORD occurred COUNT times in this year. */
    public void put(String word, int count) {
        wordstocount.put(word, count);
        Set<String> temp = new HashSet<String>();
        Number c = count;
        if (counttowords.containsKey(count)) {
            counttowords.get(c).add(word);
        } else {
            temp.add(word);
            counttowords.put(count, temp);
        }
        cached = false;
        size += 1;
    }
    /** Returns the number of words recorded this year. */
    public int size() {
        return size;
    }
    /** Returns all words in ascending order of count. */
    public Collection<String> words() {
        Collection<String> words = new ArrayList<String>();
        for (Number count: counts()) {   
            words.addAll(counttowords.get(count));
        } 
        return words;
    }
    /** Returns all counts in ascending order of count. */
    public Collection<Number> counts() {
        Collection<Number> c = counttowords.keySet();
        return c;
    }

    /** Returns rank of WORD. Most common word is rank 1. 
      * If two words have the same rank, break ties arbitrarily. 
      * No two words should have the same rank.
      */
    public int rank(String word) {
        if (!cached) {
            int i = size();
            for (Set<String> wordset : counttowords.values()) {
                for (String w : wordset) {    
                    rankMap.put(w, i--);
                }
            }
            cached = true;
        }
        return (Integer) rankMap.get(word);
    }
}
