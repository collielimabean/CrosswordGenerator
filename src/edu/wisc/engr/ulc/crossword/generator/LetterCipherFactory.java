package edu.wisc.engr.ulc.crossword.generator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The LetterCipherFactory exposes a helper method to generate a pseudorandom mapping of English
 * alphabet letters to numbers. A bijection between the letter and number exists and is guaranteed.
 * 
 * @author William Jen
 * @since 3/27/2015
 */
public class LetterCipherFactory
{
    private static final int LETTERS_IN_ALPHABET = 26;
    
    /**
     * Generates a random mapping from letter to integer.
     * @return a Map containing a mapping of alphabet to integer, where the integer
     * is bound from 1 to 26.
     */
    public static Map<Character, Integer> generate()
    {
        List<Character> letters = new ArrayList<>(LETTERS_IN_ALPHABET);
        
        for (int i = 0; i < LETTERS_IN_ALPHABET; i++)
            letters.add((char) (i + 'a'));
        
        Collections.shuffle(letters);
        
        Map<Character, Integer> cipher = new HashMap<>(LETTERS_IN_ALPHABET);
        
        for (int i = 0; i < LETTERS_IN_ALPHABET; i++)
            cipher.put(letters.get(i), i + 1);
        
        return cipher;
        
    }
    
    private LetterCipherFactory()
    {
    }
}
