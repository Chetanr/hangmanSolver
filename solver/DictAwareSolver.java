package solver;

import java.util.*;


/**
 * Dictionary aware guessing strategy for Hangman. (task B)
 * You'll need to complete the implementation of this.
 *
 * @author Jeffrey Chan, RMIT 2020
 */
public class DictAwareSolver extends HangmanSolver
{

    //this is used to check if makeGuess is called for the first time
    boolean firstGuess = true;


    //stores the length of the word to be guessed
    int[] wordLength;


    //this string stores the already guessed characters
    String guessedCharacters;

    //this is used to store the dictionary
    Set<String> dictionary = new HashSet<>();


    //this is used to maintain a temporary based on the length of the word to be guessed
    ArrayList<String> guessDictionary;


    //used to store the characters and their frequency
    HashMap<Character, Integer> characterHashMap;


    /*
        setter for characterHashMap variable
     */
    public void setCharacterHashMap(Character c) {
        this.characterHashMap.put(c , this.characterHashMap.get(c) + 1);
    }


    /*
        getter for characterHashMap variable
     */
    public HashMap<Character, Integer> getCharacterHashMap() {
        return this.characterHashMap;
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
        setter for dictionary variable
     */
    public void setDictionary(Set<String> dictionary) {
        this.dictionary = dictionary;
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
        this.guessDictionary = new ArrayList<>();
        this.characterHashMap = new HashMap<>();
    } // end of DictAwareSolver()


    /*  This function is used to start a new game.
        It sets the wordLength and maxIncorrectGuess global variables
        with wordLengths and maxIncorrectGuesses
     */
    @Override
    public void newGame(int[] wordLengths, int maxIncorrectGuesses)
    {
        setWordLength(wordLengths);

    } // end of newGame()


    /*
        this function makes a guess. It perfoms the below functions
        1.
            a. returns "'"(single quote) on the first call of the makeGuess() method
            b. else it initilializes all the characters to 0 and stores in a hashmap
        2. updates the guessDictionary if it is empty. This is done to
        3. removes the words that are not required. This is done by making use of successfully
           guessed characters and removing the words that do not have the characters in the
           respective positions
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
                if (j.length() == getWordLength())
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
    }


    /*
        This function removes the words that are not required. This is done by making use of successfully
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
                for (Integer position : lPosition)
                {
                    getGuessDictionary().removeIf(str -> str.indexOf(c) != position);
                }
            }
        }
    } // end of guessFeedback()

} // end of class DictAwareSolver
