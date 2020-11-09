package solver;

import java.util.*;


/**
 * Guessing strategy for Wheel of Fortune Hangman variant. (task D)
 * You'll need to complete the implementation of this.
 *
 * @author Jeffrey Chan, RMIT 2020
 */
public class WheelOfFortuneGuessSolver extends HangmanSolver
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
    public WheelOfFortuneGuessSolver(Set<String> dictionary) {
        setDictionary(dictionary);
        this.guessedCharacters = "";
        this.guessDictionary = new ArrayList<>();
        this.characterHashMap = new HashMap<>();
        this.wordLength = new int[100];

    } // end of WheelOfFortuneGuessSolver()


    @Override
    public void newGame(int[] wordLengths, int maxTries) {
        for (int i = 0 ; i < wordLengths.length ; i++)
        {
            setWordLength(wordLengths[i], i);
        }
    }

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
                for (int i = 0 ; i < this.wordLength.length ; i++)
                {
                    if (j.length() == getWordLength(i))
                    {
                        setGuessDictionary(j);
                    }
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
                        getGuessDictionary().removeIf(str -> str.indexOf(c) != position);
                    }
                }

            }
        }
    } // end of guessFeedback()

} // end of class WheelOfFortuneGuessSolver
