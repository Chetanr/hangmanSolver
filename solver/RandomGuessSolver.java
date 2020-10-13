package solver;

import java.util.*;

/**
 * Random guessing strategy for Hangman. (task A)
 * You'll need to complete the implementation of this.
 *
 * @author Jeffrey Chan, RMIT 2020
 */
public class RandomGuessSolver extends HangmanSolver
{
    int[] wordLength;
    int count;
    Set<String> dictionary;
    String guessedCharacters;


    public void setGuessedCharacters(char guessedCharacter) {
    this.guessedCharacters += guessedCharacter;
}

    public String getGuessedCharacters() {
        return this.guessedCharacters;
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
        this.count = 0;
        this.guessedCharacters = "";
    } // end of RandomGuessSolver()


    @Override
    public void newGame(int[] wordLengths, int maxIncorrectGuesses) {
        setWordLength(wordLengths);
    } // end of newGame()


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

        return c;
    } // end of makeGuess()


    @Override
    public void guessFeedback(char c, Boolean bGuess, ArrayList< ArrayList<Integer> > lPositions)
    {
        // Implement me!
    } // end of guessFeedback()

} // end of class RandomGuessSolver
