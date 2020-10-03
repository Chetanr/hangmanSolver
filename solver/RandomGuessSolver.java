package solver;

import java.util.*;
import java.lang.System;

/**
 * Random guessing strategy for Hangman. (task A)
 * You'll need to complete the implementation of this.
 *
 * @author Jeffrey Chan, RMIT 2020
 */
public class RandomGuessSolver extends HangmanSolver
{
    int maxIncorrectGuesss = 0;
    int[] wordLength;
    Set<String> dictionary;
    String guessedCharacters = "";


    public void setGuessedCharacters(char guessedCharacter) {
    this.guessedCharacters += guessedCharacter;
}

    public String getGuessedCharacters() {
        return this.guessedCharacters;
    }

    public Set<String> getDictionary() {
        return dictionary;
    }

    public void setMaxIncorrectGuesss(int maxIncorrectGuesss) {
        this.maxIncorrectGuesss = maxIncorrectGuesss;
    }

    public void setWordLength(int[] wordLength) {
        this.wordLength = wordLength;
    }


    /**
     * Constructor.
     *
     * @param dictionary Dictionary of words that the guessed words are drawn from.
     */
    public RandomGuessSolver(Set<String> dictionary) {
        this.dictionary = dictionary;
    } // end of RandomGuessSolver()


    @Override
    public void newGame(int[] wordLengths, int maxIncorrectGuesses) {
        setMaxIncorrectGuesss(maxIncorrectGuesses);
        setWordLength(wordLengths);
    } // end of newGame()


    @Override
    public char makeGuess() {
        Random r = new Random();
        String charString = "abcdefghijklmnopqrstuvwxyz'";

        char c = charString.charAt(r.nextInt(charString.length()));

        //this loop maskes sure to not repeat the same character again
        while (getGuessedCharacters().indexOf(c) != -1 && getGuessedCharacters().length() > 0)
        {
            c = charString.charAt(r.nextInt(charString.length()));
        }

        //set the guessedCharacter
        setGuessedCharacters(c);

        return c;
    } // end of makeGuess()


    @Override
    public void guessFeedback(char c, Boolean bGuess, ArrayList< ArrayList<Integer> > lPositions)
    {
        // Implement me!
    } // end of guessFeedback()

} // end of class RandomGuessSolver
