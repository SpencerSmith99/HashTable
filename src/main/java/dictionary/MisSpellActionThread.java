/* Authors: Spencer Smith, Anna Fortenberry
 * This thread contains the application that will be animated.
 */

package dictionary;

import java.io.*;
import java.util.*;
import javafx.application.Platform;

public class MisSpellActionThread implements Runnable {

    DictionaryController controller;
    private final String textFileName;
    private final String dictionaryFileName;

    private LinesToDisplay myLines;
    private DictionaryInterface<String, String> myDictionary;
    private boolean dictionaryLoaded;

    // Constructor for MissSpelledActionThread
    public MisSpellActionThread(DictionaryController controller) {
        super();

        this.controller = controller;
        textFileName = "src/main/resources/dictionary/check.txt";
        dictionaryFileName = "src/main/resources/dictionary/sampleDictionary.txt";

        myDictionary = new HashedMapAdaptor<String, String>();
        myLines = new LinesToDisplay();
        dictionaryLoaded = false;
    }

    @Override
    public void run() {

        loadDictionary(dictionaryFileName, myDictionary);

        Platform.runLater(() -> {
            if (dictionaryLoaded) {
               controller.SetMsg("The Dictionary has been loaded"); 
            } else {
               controller.SetMsg("No Dictionary is loaded"); 
            }
        });

        // Checkwords will set the status of each word from text file. Either it is present
        // in the hash table or not (mispelled).
        checkWords(textFileName, myDictionary);
    }

    // Function: Load Dictionary
    /* Parameters:  string filename - file of words to fill dictionary, dictionary data structure
     * Description: This function fills the dictionary data structure will all the words that will
     *              we flagged as "spelled correctly."
     */
    public void loadDictionary(String theFileName, DictionaryInterface<String, String> theDictionary) {

        Scanner input;
        try {
            String inString;
            String correctWord;

            input = new Scanner(new File(theFileName));

            while (input.hasNext()) // read until end of file
            {
                correctWord = input.next();
                theDictionary.add(correctWord, correctWord);
            }
            dictionaryLoaded = true;

        } catch (IOException e) {
            System.out.println("There was an error in reading or opening the file: " + theFileName);
            System.out.println(e.getMessage());
        }
    }

    // Function: Check Words
    /* Parameters:  String file name, dictionary data structure
       Description: This function extracts each word from an input file and checks whether it
                    is present in the hash table (spelled correctly). In terms of classes, it
                    puts "Wordlets" into myLines. Once a single line from the input file has
                    been read, it does an animation step to wait for the user.
     */
    public void checkWords(String theFileName, DictionaryInterface<String, String> theDictionary) {

        Scanner input;          // scanner variable to iterate through words in text file

        try {
            String inString;    // each line from file
            String aWord;       // single word from file

            input = new Scanner(new File(theFileName));

            // test iterates line by line until end of file
            while (input.hasNextLine())
            {
                inString = input.nextLine();
                StringTokenizer st = new StringTokenizer(inString);

                while (st.hasMoreTokens())
                {
                    aWord = st.nextToken();

                    if (checkWord(aWord, theDictionary)) {
                        Wordlet currWord = new Wordlet(aWord, true); // create a Wordlet that is spelled correctly
                        myLines.addWordlet(currWord);
                    } else {
                        Wordlet currWord = new Wordlet(aWord, false); // create a Wordlet that is spelled incorrectly
                        myLines.addWordlet(currWord);
                    }
                }
                showLines(myLines);
                myLines.nextLine(); // create a new line for next iteration
            }

        } catch (IOException e) {
            System.out.println("There was an error in reading or opening the file: " + theFileName);
            System.out.println(e.getMessage());
        }
    }

    // Function: Check for Presence of Word in Hash Table
    /* Parameters: aWord (current word from Scanner), myDictionary (implmentation of Hash Table)

     */
    public boolean checkWord(String aWord, DictionaryInterface<String, String> theDictionary) {

        boolean result = false;

        if (theDictionary.contains(aWord))
            result = true;

        return result;
    }

    private void showLines(LinesToDisplay lines) {
        try {
            Thread.sleep(500);
            Platform.runLater(() -> {
                if (myLines != null) {
                    controller.UpdateView(lines);
                }
            });
        } catch (InterruptedException ex) {
        }
    }

} // end class MisspellActionThread

