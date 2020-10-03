package solver;

import java.util.*;
import java.util.regex.*;


/**
 * Dictionary aware guessing strategy for Hangman. (task B)
 * You'll need to complete the implementation of this.
 *
 * @author Jeffrey Chan, RMIT 2020
 */
public class DictAwareSolver extends HangmanSolver
{
    int maxIncorrectGuess = 0;
    int[] wordLength;
    String guessedCharacters = "";
    Set<String> dictionary = new HashSet<String>();
    HashSet<String> guessDictionary = new HashSet<String>();

    public HashSet<String> getGuessDictionary() {
        return guessDictionary;
    }

    public void setGuessDictionary(String word) {
        this.guessDictionary.add(word);
    }

    public void setDictionary(Set<String> dictionary) {
        this.dictionary = dictionary;
    }

    public Set<String> getDictionary() {
        return dictionary;
    }

    public void setGuessedCharacters(char guessedCharacter) {
        this.guessedCharacters += guessedCharacter;
    }

    public String getGuessedCharacters() {
        return this.guessedCharacters;
    }

    public int getMaxIncorrectGuess() {
        return maxIncorrectGuess;
    }

    public void setMaxIncorrectGuess(int maxIncorrectGuess) {
        this.maxIncorrectGuess = maxIncorrectGuess;
    }

    public int getWordLength() {
        return wordLength[0];
    }

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
    } // end of DictAwareSolver()


    /*  This function is used to start a new game.
        It sets the wordLength and maxIncorrectGuess global variables
        with wordLengths and maxIncorrectGuesses
     */
    @Override
    public void newGame(int[] wordLengths, int maxIncorrectGuesses)
    {
        setWordLength(wordLengths);
        setMaxIncorrectGuess(maxIncorrectGuesses);
    } // end of newGame()


    //this function makes a guess
    @Override
    public char makeGuess() {
        Random r = new Random();
        String charString = "abcdefghijklmnopqrstuvwxyz'";

        char c = charString.charAt(r.nextInt(charString.length()));

        //  this loop makes sure to not guess the same character again
        while (getGuessedCharacters().indexOf(c) != -1 && getGuessedCharacters().length() > 0)
        {
            c = charString.charAt(r.nextInt(charString.length()));
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
                    Pattern pattern2 = Pattern.compile(Character.toString(c), Pattern.CASE_INSENSITIVE);
                    Matcher matcher2 = pattern2.matcher(j);
                    if (matcher2.find()) {
                        setGuessDictionary(j);
                    }
                }
            }
        }


        /*  this loop
            1. returns a character if a word in the temporary dictionary (guessDictionary) contains that character
            2. updates the temporary dictionary (guessDictionary) if there is no such word
        */
        for (String i : getGuessDictionary()) {
            Pattern pattern1 = Pattern.compile(Character.toString(c), Pattern.CASE_INSENSITIVE);
            Matcher matcher1 = pattern1.matcher(i);
            if (matcher1.find()) {
                break;
            } else {
                for (String j : getDictionary()) {
                    if (j.length() == getWordLength()) {
                        Pattern pattern2 = Pattern.compile(Character.toString(c), Pattern.CASE_INSENSITIVE);
                        Matcher matcher2 = pattern2.matcher(j);
                        if (matcher2.find()) {
                            setGuessDictionary(i);
                        }
                    }
                }
            }
        }
        return c;
    } // end of makeGuess()


    @Override
    public void guessFeedback(char c, Boolean bGuess, ArrayList< ArrayList<Integer> > lPositions)
    {
        // Implement me!
    } // end of guessFeedback()

} // end of class DictAwareSolver
