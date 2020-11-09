package solver;

import java.util.*;

/**
 * Guessing strategy for two word Hangman. (task C)
 * You'll need to complete the implementation of this.
 *
 * @author Jeffrey Chan, RMIT 2020
 */
public class TwoWordHangmanGuessSolver extends HangmanSolver
{

    //this is used to check if makeGuess is called for the first time
    boolean firstGuess = true;


    //stores the length of the word to be guessed
    int[] wordLength;


    //this string stores the already guessed characters
    String guessedCharacters;


    //this is used to store the dictionary
    Set<String> dictionary = new HashSet<>();


    //this is used to maintain a temporary based on the length of the first word to be guessed
    ArrayList<String> guessDictionary;


    //used to store the characters and their frequency
    HashMap<Character, Integer> characterHashMap;


    /*
        getter for characterHashMap variable
     */
    public HashMap<Character, Integer> getCharacterHashMap() {
        return this.characterHashMap;
    }


    /*
        setter for characterHashMap variable
     */
    public void setCharacterHashMap(Character c) {
        this.characterHashMap.put(c , this.characterHashMap.get(c) + 1);
    }


    /*
        setter for dictionary variable
     */
    public void setDictionary(Set<String> dictionary) {
        this.dictionary = dictionary;
    }


    /*
        setter for wordLength variable
     */
    public void setWordLength(int wordLength, int i) {
        this.wordLength[i] = wordLength;
    }


    /*
       getter for wordLength variable
    */
    public int getWordLength(int i) {
        return this.wordLength[i];
    }


    /*
        getter for guessDictionary1 variable
     */
    public ArrayList<String> getGuessDictionary() {
        return this.guessDictionary;
    }


    /*
        setter for guessDictionary1 variable
     */
    public void setGuessDictionary(String word) {
        this.guessDictionary.add(word);
    }


    /*
        getter for dictionary variable
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

    /**
     * Constructor.
     *
     * @param dictionary Dictionary of words that the guessed words are drawn from.
     */
    public TwoWordHangmanGuessSolver(Set<String> dictionary) {
        setDictionary(dictionary);
        this.guessedCharacters = "";
        this.guessDictionary = new ArrayList<>();
        this.characterHashMap = new HashMap<>();
        this.wordLength = new int[2];
    } // end of TwoWordHangmanGuessSolver()


    @Override
    public void newGame(int[] wordLengths, int maxIncorrectGuesses) {
        for (int i = 0 ; i < wordLengths.length ; i++)
        {
            setWordLength(wordLengths[i], i);
        }

    } // end of newGame()


    /*
         this function makes a guess. It perfoms the below functions
         1.
             a. returns "'"(single quote) on the first call of the makeGuess() method
             b. else it initilializes all the characters to 0 and stores in a hashmap
         2. updates the guessDictionary1 and guessDictionary2 if it is empty. This is done to
            narrow down the list of words to only those words that match the length of the word
            to be guessed
     */
    @Override
    public char makeGuess() {
        if (firstGuess)
        {
            firstGuess = false;
            return '\'';
        }
        else
        {
            this.characterHashMap.clear();
            for (char ch = 'a' ; ch <= 'z' ; ch++)
            {
                this.characterHashMap.put(ch , 0);
            }
        }

        if (getGuessDictionary().isEmpty())
        {
            for (String j : getDictionary())
            {
                if (j.length() == getWordLength(0) || j.length() == getWordLength(1))
                {
                    setGuessDictionary(j);
                }
            }
        }

        
        for (Character i : getCharacterHashMap().keySet())
        {
            for (String str : getGuessDictionary())
            {
                if (str.indexOf(i) >= 0)
                {
                    setCharacterHashMap(i);
                }
            }
        }

        char ch = Collections.max(getCharacterHashMap().entrySet(), Map.Entry.comparingByValue()).getKey();

        while (getGuessedCharacters().indexOf(ch) != -1 && getGuessedCharacters().length() > 0)
        {
            ch = Collections.max(getCharacterHashMap().entrySet(), Map.Entry.comparingByValue()).getKey();
            getCharacterHashMap().put(ch , 0);
        }


        setGuessedCharacters(ch);

        return ch;
    } // end of makeGuess()


    /*
        1. This function is used to store a correctly guessed character
            in feedback variable
        2. It also removes the words that are not required. This is done by making use of successfully
           guessed characters and removing the words that do not have the characters in the
           respective positions from guessDictionary
     */
    @Override
    public void guessFeedback(char c, Boolean bGuess, ArrayList< ArrayList<Integer> > lPositions)
    {
        if (bGuess)
        {
            for (ArrayList<Integer> lPosition : lPositions)
            {
                if (lPosition != null)
                {
                    for (Integer position : lPosition)
                    {
                        getGuessDictionary().removeIf(str -> (str.indexOf(c) != position));
                    }
                }

            }
        }
    } // end of guessFeedback()

} // end of class TwoWordHangmanGuessSolver
