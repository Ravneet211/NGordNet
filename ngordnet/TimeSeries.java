package ngordnet;
import java.util.TreeMap;
import java.util.NavigableSet;
import java.util.Collection;
import java.util.TreeSet;
import java.util.ArrayList;
public class TimeSeries<T extends Number> extends TreeMap<Integer, T> {    
    /** Constructs a new empty TimeSeries. */
    public TimeSeries() {
        this.clear();
    }

    /** Returns the years in which this time series is valid. Doesn't really
      * need to be a NavigableSet. This is a private method and you don't have 
      * to implement it if you don't want to. */
    private NavigableSet<Integer> validYears(int startYear, int endYear) {
        TreeSet<Integer> n = new TreeSet<Integer>();
        for (; startYear <= endYear; startYear++) {
            n.add(startYear);
        }
        return n;
    }

    /** Creates a copy of TS, but only between STARTYEAR and ENDYEAR. 
     * inclusive of both end points. */
    public TimeSeries(TimeSeries<T> ts, int startYear, int endYear) {
        NavigableSet<Integer> validYears = validYears(startYear, endYear);
        for (Number year : ts.years()) {
            if (validYears.contains(year)) {
                this.put((Integer) year, ts.get(year));
            }
        }
    }

    /** Creates a copy of TS. */
    public TimeSeries(TimeSeries<T> ts) {
        for (Number year : ts.years()) {
            this.put((Integer) year, ts.get(year));
        }
    }

    /** Returns the quotient of this time series divided by the relevant value in ts.
      * If ts is missing a key in this time series, return an IllegalArgumentException. */
    public TimeSeries<Double> dividedBy(TimeSeries<? extends Number> ts) {
        TimeSeries<Double> t = new TimeSeries<Double>();
        for (Number year: years()) {
            T thisvalue = this.get(year);
            Number tsvalue = ts.get(year);
            if (tsvalue == null) {
                throw new IllegalArgumentException();
            }
            t.put((Integer) year, thisvalue.doubleValue() / tsvalue.doubleValue());
        }
        return t;
    }

    /** Returns the sum of this time series with the given ts. The result is a 
      * a Double time series (for simplicity). */
    public TimeSeries<Double> plus(TimeSeries<? extends Number> ts) {
        TimeSeries<Double> t = new TimeSeries<Double>();
        Collection<Number> years = years();
        years.addAll(ts.years());
        for (Number year: years) {
            T thisvalue = this.get(year);
            Number tsvalue = ts.get(year);
            if (tsvalue == null) {
                t.put((Integer) year, thisvalue.doubleValue());
            }    else if (thisvalue == null) {
                t.put((Integer) year, tsvalue.doubleValue());
            }    else {
                t.put((Integer) year, thisvalue.doubleValue() + tsvalue.doubleValue());
            }
        }
        return t;
    }

    /** Returns all years for this time series (in any order). */
    public Collection<Number> years() {
        TreeSet<Number> t = new TreeSet<Number>();
        for (Integer year:keySet()) {
            t.add(year);
        }
        return t;
    }

    /** Returns all data for this time series. 
      * Must be in the same order as years(). */
    public Collection<Number> data() {
        ArrayList<Number> t = new ArrayList<Number>();
        T temp;
        for (Number year:years()) {
            temp = get((Integer) year);
            t.add((Number) temp);
        }
        return t;
    }
}
