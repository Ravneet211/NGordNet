/* Starter code for NgordnetUI (part 7 of the project). Rename this file and 
   remove this comment. */

package ngordnet;
import edu.princeton.cs.introcs.StdIn;
import edu.princeton.cs.introcs.In;
import java.util.Set;

/** Provides a simple user interface for exploring WordNet and NGram data.
 *  @author [yournamehere mcjones]
 */
public class NgordnetUI {
    private static final int SD = 1505;
    private static final int ED = 2000;
    public static void main(String[] args) {
        In in = new In("./ngordnet/ngordnetui.config");
        System.out.println("Reading ngordnetui.config...");

        String wordFile = in.readString();
        String countFile = in.readString();
        String synsetFile = in.readString();
        String hyponymFile = in.readString();
        System.out.println("\nBased on ngordnetui.config, using the following: "
                           + wordFile + ", " + countFile + ", " + synsetFile +
                           ", and " + hyponymFile + ".");
        NGramMap ngm = new NGramMap(wordFile,countFile);
        WordNet wn = new WordNet(synsetFile,hyponymFile);
        Plotter pl = new Plotter();
        int startDate = SD;
        int endDate = ED;
        while (true) {
            System.out.print("> ");
            String line = StdIn.readLine();
            String[] rawTokens = line.split(" ");
            String command = rawTokens[0];
            String[] tokens = new String[rawTokens.length - 1];
            System.arraycopy(rawTokens, 1, tokens, 0, rawTokens.length - 1);
            try {
            switch (command) {
                case "quit":
                if (tokens.length == 0) {
                    return;
                }
                break;
                case "help":
                    if (tokens.length == 0) {
                        in = new In("./demos/help.txt");
                        String helpStr = in.readAll();
                        System.out.println(helpStr);
                    }
                    break;  
                case "range":
                        startDate = Integer.parseInt(tokens[0]); 
                        endDate = Integer.parseInt(tokens[1]); 
                case "hyponyms":
                    for(int j = 0; j < tokens.length; j++) {
                      System.out.println(wn.hyponyms(tokens[j]));
                    }
                    break;
                    case "history":
                      pl.plotAllWords(ngm,tokens,startDate,endDate);
                    break;
                
                case "hypohist":
                    pl.plotCategoryWeights(ngm, wn, tokens, startDate, endDate);
                    break;
                case "count":
                   System.out.println(ngm.countInYear(tokens[0],Integer.parseInt(tokens[1])));
                   break;
                case "wordlength":
                    if(tokens.length == 0) {
                        pl.plotProcessedHistory(ngm,startDate,endDate,new WordLengthProcessor());
                    }
                    break;
                case "zipf":
                    if(tokens.length == 1) {
                        pl.plotZipfsLaw(ngm, Integer.parseInt(tokens[0]));
                    }
                    break;
                default:
                    System.out.println("Invalid command.");  
                    break;
            }
        }
        catch(NullPointerException n) {
            System.out.println("Null argument");
        }
        catch(IllegalArgumentException i){
            System.out.println("Wrong type of arguments");
        }
        catch(ArrayIndexOutOfBoundsException a){
            System.out.println("") ;
        }
        }
    }
} 


 
