package edu.wisc.engr.ulc.crossword.generator;

import java.util.Collections;
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
    private Set<Letter> usedLetters;
    private Map<Character, Bag<Letter>> availableBranchLetters;
    
    public Crossword()
    {
        usedLetters = new HashSet<>();
        availableBranchLetters = new HashMap<>();
        
        // create a bag for each letter
        for (char c = 'a'; c <= 'z'; c++)
            availableBranchLetters.put(c, new Bag<Letter>());
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
            
            // TODO: general case
            // create shuffled stack of letters
            // so we can cycle through all of them if necessary
            Stack<IndexedCharacter> idCh = new Stack<>();
            char[] word_charr = word.toCharArray();

            for (int i = 0; i < word_charr.length; i++)
                idCh.add(new IndexedCharacter(i, word_charr[i]));
                
            Collections.shuffle(idCh);

            // branch selection
            IndexedCharacter c = null;
            Letter chosenBranch = null;
            while (!idCh.empty())
            {
                c = idCh.pop();
                Bag<Letter> branchBag = availableBranchLetters.get(c);
                
                // no available coordinates
                if (branchBag.size() == 0)
                    continue;
                
                // iterate over the bag
                // attempt to get a good coordinate
                // that can fit the popped word
                for (Letter l : branchBag)
                {       
                    // get the closest coordinates
                    // if l.orient == vertical, check left and right.
                    // if l.orient == horizontal, check up and down.
                    // note the flipped nature - chosenBranch must have
                    // opposite orientation of to-be-inserted word
                    
                    // difference of 1 is OKAY if end letter matches
                    // otherwise min dist is 2. 
                    // if conditions satisfied, set chosenBranch, and break!
                }
            }

            // failure case - couldn't find a branch to use
            if (chosenBranch == null)
            {
                failed.add(word);
                continue;
            }

            // get orientation and initial coordinate
            // false warning - chosenBranch will be set.
            Coordinate newWordCoord = null;
            WordOrientation newOrient;
            if (chosenBranch.getParent().getOrientation() == WordOrientation.VERTICAL)
            {
                newOrient = WordOrientation.HORIZONTAL;
                newWordCoord = new Coordinate(chosenBranch.getCoordinate().getX() - c.getIndex(), chosenBranch.getCoordinate().getY());
            }
            else
            {
                newOrient = WordOrientation.VERTICAL;
                newWordCoord = new Coordinate(chosenBranch.getCoordinate().getX(), chosenBranch.getCoordinate().getY() + c.getIndex());
            }

            Word newWord = new Word(word, newWordCoord, newOrient);
            chosenBranch.setBranch(newWord);
            registerWord(newWord);
        }
        
        return true;
    }
    
    private Letter getLetterFromCoordinate(Coordinate coord)
    {
        Letter found = null;
        for (Letter l : usedLetters)
        {
            if (l.getCoordinate().equals(coord))
            {
                found = l;
                break;
            }
        }
        
        return found;
    }
    
    private void registerWord(Word word)
    {
        for (Letter l : word.getLetters())
        {
            usedLetters.add(l);
            
            // if the letter does not have a branch
            // add it into the bag
            if (l.getBranch() == null)
            {
                Bag<Letter> coords = availableBranchLetters.get(l.getCharacter());
                coords.add(l);
            }
        }
    }
    
    private class IndexedCharacter
    {
        private final int index;
        private final char character;
        
        public IndexedCharacter(int index, char c)
        {
            this.index = index;
            this.character = c;
        }
        
        public int getIndex()
        {
            return index;
        }
        
        public char getCharacter()
        {
            return character;
        }
    }
}
