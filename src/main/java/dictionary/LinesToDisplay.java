/* Authors: Spencer Smith, Anna Fortenberry
 * This class is uses to display the lines of text from the input file that are corrected.
 */

package dictionary;

import java.util.Iterator;

public class LinesToDisplay {

    public static final int LINES = 10;     // Display 10 lines
    private AList<Wordlet>[] lines;         // AList Structure Vector of Wordlets
    private int currentLine;

    // Constructor for LinesToDisplay
    public LinesToDisplay() {

        lines = (AList<Wordlet>[]) new AList[LINES + 1];
        lines[0] = new AList<>();
        currentLine = 0;

    }

    // Function: Add Wordlet to Current Line
    /* Parameter:   Wordlet
       Description: This function accesses the array of lines at
                    the current index and calls .add(w) from the
                    AList class.
    */
    public void addWordlet(Wordlet w) {

        lines[currentLine].add(w);

    }

    // Function: Go to Next Line
    /* Description: If adding a new line does not exceed the maximum capacity,
                    create space for a new line (increament array lines with
                    space for a new AList vector of Wordlets)

     */
    public void nextLine() {

        if (currentLine < LINES) {
            currentLine++;
        } else {
            for (int i = 0; i < LINES; i++) {
                // move each line down by one
                lines[i] = lines[i + 1];
            }  // end for
        }
        // always start a new list
        lines[currentLine] = new AList<Wordlet>();
    }

      
    public int getCurrentLine(){
        return currentLine;
    }
    
    public AList<Wordlet>[] getLines(){
        return lines;
    }
}
