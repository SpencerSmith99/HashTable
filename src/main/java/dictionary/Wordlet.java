/* Authors: Spencer Smith, Anna Fortenberry
 * Class Wordlet for a single word extracted from input text file.
 */

package dictionary;

public class Wordlet
{
    String myWord;              // single word
    boolean spelledCorrectly;   // return of whether word is in hash table

    // Constructor for Wordlet Class
    public Wordlet(String word, boolean spelling)
    {
        myWord = word;
        spelledCorrectly = spelling;
    }

    // Getter: return word stored in instance of class Wordlet
    public String getWord()
    {
        return myWord;
    }

    // Getter: return flag for whether word is present in hash table or not
    public boolean isSpelledCorrectly()
    {
        return spelledCorrectly;
    }
}
