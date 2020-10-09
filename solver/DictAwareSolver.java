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
    int maxIncorrectGuess;
    int count;
    int[] wordLength;
    String guessedCharacters;
    Set<String> dictionary = new HashSet<>();
    ArrayList<String> guessDictionary;
    HashMap <Character, Integer> feedback;

    public void setFeedback(Character c, int i) {
        this.feedback.put(c , i);
    }

    public HashMap<Character, Integer> getFeedback() {
        return this.feedback;
    }


    public List<String> getGuessDictionary() {
        return this.guessDictionary;
    }

    public void setGuessDictionary(String word) {
        this.guessDictionary.add(word);
    }

    public void setDictionary(Set<String> dictionary) {
        this.dictionary = dictionary;
    }

    public Set<String> getDictionary() {
        return this.dictionary;
    }

    public void setGuessedCharacters(char guessedCharacter) {
        this.guessedCharacters += guessedCharacter;
    }

    public String getGuessedCharacters() {
        return this.guessedCharacters;
    }

    public void setMaxIncorrectGuess(int maxIncorrectGuess) {
        this.maxIncorrectGuess = maxIncorrectGuess;
    }

    public int getWordLength() {
        return this.wordLength[0];
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
        this.maxIncorrectGuess = 0;
        this.guessedCharacters = "";
        this.count = 0;
        this.feedback = new HashMap<>();
        this.guessDictionary = new ArrayList<>();

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
        }



        return c;
    } // end of makeGuess()


    @Override
    public void guessFeedback(char c, Boolean bGuess, ArrayList< ArrayList<Integer> > lPositions)
    {
        if (bGuess)
        {
            for (ArrayList<Integer> lPosition : lPositions) {
                for (Integer integer : lPosition) {
                    setFeedback(c, integer);
                }
            }
        }
        // Implement me!
    } // end of guessFeedback()

} // end of class DictAwareSolver
