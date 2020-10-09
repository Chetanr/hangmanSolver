package solver;

import java.util.*;
import java.util.regex.Pattern;


/**
 * Dictionary aware guessing strategy for Hangman. (task B)
 * You'll need to complete the implementation of this.
 *
 * @author Jeffrey Chan, RMIT 2020
 */
public class DictAwareSolver extends HangmanSolver
{

    //this is used to make guesses in the makeGuess() method
    int count;


    //stores the length of the word to be guessed
    int[] wordLength;


    //used to check if the possibleWord is empty
    boolean isEmpty;

    //this string stores the already guessed characters
    String guessedCharacters;

    //this is used to store the dictionary
    Set<String> dictionary = new HashSet<>();


    //this is used to maintain a temporary based on the length of the word to be guessed
    ArrayList<String> guessDictionary;


    //this is used to store the correct guesses and compare with the words in guessDictionary
    HashMap <Character, Integer> feedback;

    String possibleWord;


    /*
        setter for feedback variable
     */
    public void setFeedback(Character c, int i) {
        this.feedback.put(c , i);
    }


    /*
        getter for feedback variable
     */
    public HashMap<Character, Integer> getFeedback() {
        return this.feedback;
    }


    /*
        getter for guessDictionary variable
     */
    public ArrayList<String> getGuessDictionary() {
        return this.guessDictionary;
    }


    /*
        setter for guessDictionary variable
     */
    public void setGuessDictionary(String word) {
        this.guessDictionary.add(word);
    }


    /*
        getter for dictionary variable
     */
    public void setDictionary(Set<String> dictionary) {
        this.dictionary = dictionary;
    }


    /*
        setter for dictionary variable
     */
    public Set<String> getDictionary() {
        return this.dictionary;
    }


    /*
        setter for guessedCharacters variable
     */
    public void setGuessedCharacters(char guessedCharacter) {
        this.guessedCharacters += guessedCharacter;
    }


    /*
        getter for guessedCharacters variable
     */
    public String getGuessedCharacters() {
        return this.guessedCharacters;
    }


    /*
        getter for wordLength variable
     */
    public int getWordLength() {
        return this.wordLength[0];
    }


    /*
        setter for wordLength variable
     */
    public void setWordLength(int[] wordLength) {
        this.wordLength = wordLength;
    }

    /**
     * Constructor.
     *
     * @param dictionary Dictionary of words that the guessed words are drawn from.
     */
    public DictAwareSolver(Set<String> dictionary) {
        setDictionary(dictionary);
        this.guessedCharacters = "";
        this.count = 0;
        this.feedback = new HashMap<>();
        this.guessDictionary = new ArrayList<>();
        this.possibleWord = "";
        this.isEmpty = true;
    } // end of DictAwareSolver()


    /*  This function is used to start a new game.
        It sets the wordLength and maxIncorrectGuess global variables
        with wordLengths and maxIncorrectGuesses
     */
    @Override
    public void newGame(int[] wordLengths, int maxIncorrectGuesses)
    {
        setWordLength(wordLengths);
        for (int i = 0 ; i < getWordLength() ; i++)
            this.possibleWord += ".";
    } // end of newGame()


    /*
        this function makes a guess. It perfoms the below functions
        1. A unique character is generated
        2. Update the guessDictionary when makeGuess() is called for the first time
           based on the length of the word to be guessed
        3. Check the guessDictionary and eliminate the words that are not required.
           The words are removed if they do not match the positions of the characters
           in feedback variable
     */
    @Override
    public char makeGuess() {
        Random r = new Random();
        String charString = "bcdfghjklmnpqrstvwxyz'";
        String vowels = "aeiou";


        char c;

        if (count < 5)
        {
            c = vowels.charAt(r.nextInt(vowels.length()));
            while (getGuessedCharacters().indexOf(c) != -1 && getGuessedCharacters().length() > 0)
            {
                c = vowels.charAt(r.nextInt(vowels.length()));
            }
            count ++;
        }
        else
        {
            c = charString.charAt(r.nextInt(charString.length()));
            while (getGuessedCharacters().indexOf(c) != -1 && getGuessedCharacters().length() > 0)
            {
                c = charString.charAt(r.nextInt(charString.length()));
            }
        }

        /*  Set the guessedCharacter String.
            This is done to make sure no duplicate guessing of the character is done
         */
        setGuessedCharacters(c);


        /* this is used to set the temporary dictionary (guessDictionary) with the words
            of the length of the word to be guessed for the first time
         */
        if (getGuessDictionary().isEmpty())
        {
            for (String j : getDictionary())
            {
                if (j.length() == getWordLength())
                {
                    setGuessDictionary(j);
                }
            }
        }

        //sort the arraylist in ascending order
        Collections.sort(getGuessDictionary());

        int numCh = 0;
        for (int i = 0 ; i < getGuessDictionary().size() ; i++)
        {

            String temp = getGuessDictionary().get(i);
            for (Character ch : getFeedback().keySet())
            {
                if (temp.charAt(getFeedback().get(ch)) == ch)
                {
                    numCh++;
                }
            }
            if (numCh != getFeedback().size())
            {
                getGuessDictionary().remove(temp);
            }
            else if (numCh == getFeedback().size() && temp.indexOf(c) >= 0)
            {
               if (Pattern.matches(this.possibleWord, temp) && !this.isEmpty)
               {
                   return c;
               }
            }
        }
        return c;
    } // end of makeGuess()


    /*
        This function is used to store a correctly guessed character
        in feedback variable
     */
    @Override
    public void guessFeedback(char c, Boolean bGuess, ArrayList< ArrayList<Integer> > lPositions)
    {
        if (bGuess)
        {
            StringBuilder str = new StringBuilder(this.possibleWord);
            for (ArrayList<Integer> lPosition : lPositions)
            {
                for (Integer position : lPosition)
                {
                    setFeedback(c, position);
                    str.setCharAt(position,c);
                }
                this.possibleWord = str.toString();
                this.isEmpty = false;
            }
        }
        // Implement me!
    } // end of guessFeedback()

} // end of class DictAwareSolver
