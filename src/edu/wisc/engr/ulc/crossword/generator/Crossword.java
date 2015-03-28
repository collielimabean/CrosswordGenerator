package edu.wisc.engr.ulc.crossword.generator;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

import edu.wisc.engr.ulc.crossword.util.Bag;

/**
 * Represents a crossword puzzle.
 * @see http://en.wikipedia.org/wiki/Crossword
 * @author William Jen
 * @since 3/27/2015
 */
public class Crossword
{
    private Word root;
    private Set<Coordinate> usedCoordinates;
    private Map<Character, Bag<Coordinate>> availableCoordinates;
    
    public Crossword()
    {
        usedCoordinates = new HashSet<Coordinate>();
        availableCoordinates = new HashMap<>();
        
        for (char c = 'a'; c <= 'z'; c++)
            availableCoordinates.put(c, new Bag<Coordinate>());
    }
    
    public boolean addWordList(final Stack<String> wordList)
    {
        if (wordList == null)
            return false;
        
        // copy initial word list 
        Stack<?> words = (Stack<?>) wordList.clone();
        
        // secondary list of failed insertions
        Queue<String> failed = new LinkedList<String>();
        
        while (!words.isEmpty() || !failed.isEmpty())
        {
            String word = null;
            
            // get a word to insert
            // if initial list 
            if (!words.isEmpty())
                word = (String) words.pop();
            else if (!failed.isEmpty())
                word = failed.remove();
            else
                return true; // done
            
            // initial word case
            if (root == null)
            {
                WordOrientation initial;
                
                // choose initial orientation based on
                // length of word list
                if (words.size() % 2 == 0)
                    initial = WordOrientation.HORIZONTAL;
                else
                    initial = WordOrientation.VERTICAL;
                
                root = new Word(word, Coordinate.ORIGIN, initial);
                registerWord(root);
                continue;
            }
            
            //TODO: general case
        }
        
        return true;
    }
    
    private void registerWord(Word word)
    {
        for (Letter l : word.getLetters())
            usedCoordinates.add(l.getCoordinate());
    }
    
}
