package ngordnet;
import java.util.Map;
import java.util.HashMap;
import java.util.Collection;
import edu.princeton.cs.introcs.In;
public class InTester
{
	public static void main(String[] args)
	{	Map<String,TimeSeries<Integer>> wordsinayear = new HashMap<String,TimeSeries<Integer>>();
    	Map<Integer,YearlyRecord> recordedyears = new HashMap<Integer, YearlyRecord>();
    	TimeSeries<Long> totalcount = new TimeSeries<Long>();
		In in1 = new In("./p1data/ngrams/words_that_start_with_q.csv");
        HashMap<String,Integer> y = new HashMap<String,Integer>();
        String s;
        Integer year,count;
        String[] tokens;
        TimeSeries<Integer> t = new TimeSeries<Integer>();
    	while(in1.hasNextLine())
    	{
            s = in1.readLine();
            tokens = s.split("[\t]+");
            year=Integer.parseInt(tokens[1]);
            count=Integer.parseInt(tokens[2]);
           
             if(wordsinayear.containsKey(tokens[0]))
           	{
                wordsinayear.get(tokens[0]).put(year,count);
            }
            else
            {
            	t.clear();
            	t.put(year,count);
                wordsinayear.put(tokens[0],new TimeSeries<Integer>(t));
            }
            if(recordedyears.containsKey(year))
            {
                recordedyears.get(year).put(tokens[0],count);
            }
            else
            {	y.clear();
                y.put(tokens[0],count);
                recordedyears.put(year,new YearlyRecord(y));
            }
       
        }
	}
}
