import ngordnet.WordNet;

/** Class that demonstrates basic WordNet functionality.
 *  @author Josh Hug
 */
public class WordNetDemo {
    public static void main(String[] args) {
        WordNet wn = new WordNet("./wordnet/synsets1000-subgraph.txt", "./wordnet/hyponyms1000-subgraph.txt");

        /* These should all print true. */
        System.out.println(wn.isNoun("jump"));
        System.out.println(wn.isNoun("leap"));
        System.out.println(wn.isNoun("nasal_decongestant"));

        /* The code below should print the following: 
            All nouns:
            augmentation
            nasal_decongestant
            change
            action
            actifed
            antihistamine
            increase
            descent
            parachuting
            leap
            demotion
            jump
        */
        /*
        System.out.println("All nouns:");
        for (String noun : wn.nouns()) {
            System.out.println(noun);
        }*/

        /* The code below should print the following: 
            Hypnoyms of increase:
            augmentation
            increase
            leap
            jump
        */

        System.out.println("Hypnoyms of entity:");
        for (String noun : wn.hyponyms("entity")) {
            if(noun.equals("ABO_antibodies"))
            System.out.println(noun);
        }
    }    
} 
