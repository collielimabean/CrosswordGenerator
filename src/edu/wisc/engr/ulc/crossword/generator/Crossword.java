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
    /** Index of buffer for crossword bounds - (left) */
    private static final int LEFT_INDEX = 0;
    
    /** Index of buffer for crossword bounds - (top) */
    private static final int UP_INDEX = 1;
    
    /** Index of buffer for crossword bounds - (right) */
    private static final int RIGHT_INDEX = 2;
    
    /** Index of buffer for crossword bounds - (bottom) */
    private static final int DOWN_INDEX = 3;
    
    private Set<Letter> usedLetters;
    private Map<Character, Bag<Letter>> availableBranchLetters;
    
    /**
     * Instantiates a new Crossword instance.
     */
    public Crossword()
    {
        usedLetters = new HashSet<>();
        availableBranchLetters = new HashMap<>();
        
        // create a bag for each letter
        for (char c = 'a'; c <= 'z'; c++)
            availableBranchLetters.put(c, new Bag<Letter>());
    }
    
    /**
     * Adds the words into the Crossword.
     * @param wordList A stack of words as strings
     * @return true if all words were inserted successfully, false otherwise
     */
    public boolean addWordList(final Stack<String> wordList)
    {
        if (wordList == null)
            return false;
        
        // copy initial word list 
        Stack<?> words = (Stack<?>) wordList.clone();
        
        // secondary list of failed insertions
        Queue<String> failed = new LinkedList<String>();
        
        boolean insertAllSuccessful = true;
        
        while (!words.isEmpty() || !failed.isEmpty())
        {
            String word = null;
            boolean fromFailedQueue = false;
            
            // get a word to insert
            // if initial list 
            if (!words.isEmpty())
            {
                word = (String) words.pop();
            }
                
            else if (!failed.isEmpty())
            {
                word = failed.remove();
                fromFailedQueue = true;
            }
              
            else
            {
                return insertAllSuccessful; // done
            }
            
            // force word to lower case
            // since all maps rely on lower case ASCII
            word = word.toLowerCase();
            
            // initial word case
            if (usedLetters.isEmpty())
            {
                WordOrientation initOrientation;
                
                // choose initial orientation based on
                // length of word list
                if (words.size() % 2 == 0)
                    initOrientation = WordOrientation.HORIZONTAL;
                else
                    initOrientation = WordOrientation.VERTICAL;
                
                Word initial = new Word(word, Coordinate.ORIGIN, initOrientation);
                registerWord(initial);
                continue;
            }
            
            // general case
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
            while (!idCh.empty() && chosenBranch == null)
            {
                c = idCh.pop();
                Bag<Letter> branchBag = availableBranchLetters.get(c.getCharacter());
                
                // no available coordinates
                if (branchBag.size() == 0)
                    continue;
                    
                // iterate over the bag
                // attempt to get a good coordinate
                // that can fit the popped word
                for (Letter l : branchBag)
                {
                    assert(l.getBranch() == null);
                    
                    if (checkHorizontalWord(word, c, l) || checkVerticalWord(word, c, l))
                    {
                        chosenBranch = l;
                        assert(branchBag.remove(l) == true);
                        break;
                    }
                }
            }

            // failure case - couldn't find a branch to use
            // if failed word fails again, then throw it out
            if (chosenBranch == null)
            {
                if (!fromFailedQueue)
                    failed.add(word);
                else
                    insertAllSuccessful = false;
                
                continue;
            }

            // get orientation and initial coordinate for insertion
            Coordinate newWordCoord = null;
            WordOrientation newOrient;
            if (chosenBranch.getParent().getOrientation() == WordOrientation.VERTICAL)
            {
                newOrient = WordOrientation.HORIZONTAL;
                newWordCoord = new Coordinate(chosenBranch.getCoordinate().getX() - c.getIndex(),
                        chosenBranch.getCoordinate().getY());
            }
            else
            {
                newOrient = WordOrientation.VERTICAL;
                newWordCoord = new Coordinate(chosenBranch.getCoordinate().getX(),
                        chosenBranch.getCoordinate().getY() + c.getIndex());
            }
            
            // initialize the new word
            Word newWord = new Word(word, newWordCoord, newOrient);
            
            // set both word branches
            chosenBranch.setBranch(newWord);
            newWord.getLetters()[c.index].setBranch(chosenBranch.getParent());
            
            // register word with used coordinate tracker and available branches
            registerWord(newWord);
        }
        
        return insertAllSuccessful;
    }
    
    /**
     * Prints the Crossword to stdout.
     */
    public void printCharacterBuffer()
    {
        System.out.println("-------------------------------");
        char[][] buffer = toCharacterBuffer();
        
        if (buffer == null)
        {
            System.err.println("Failed to print.");
            return;
        }
        
        for (int i = 0; i < buffer.length; i++)
        {
            for (int j = 0; j < buffer[i].length; j++)
            {
                if (buffer[i][j] == 0)
                    System.out.print(" ");
                else
                    System.out.print(buffer[i][j]);
                
                System.out.print(" ");
            }
            
            System.out.println();
        }
        
        System.out.println("-------------------------------");
    }
    
    /**
     * Returns the Crossword object as a 2D character buffer.
     * @return the Crossword object as a 2D character buffer.
     */
    public char[][] toCharacterBuffer()
    {
        int[] bounds = getCrosswordBounds();
        
        if (bounds == null)
            return null;
        
        int width = bounds[RIGHT_INDEX] - bounds[LEFT_INDEX] + 1;
        int height = bounds[UP_INDEX] - bounds[DOWN_INDEX] + 1;
        
        char[][] buffer = new char[height][width];
        
        for (Letter l : usedLetters)
        {
            Coordinate coord = l.getCoordinate();
            
            // transform from root origin to buffer
            int row = bounds[UP_INDEX] - coord.getY();
            int col = coord.getX() - bounds[LEFT_INDEX];
            
            buffer[row][col] = l.getCharacter();
        }
        
        return buffer;
    }
    
    // TODO - improve checking of branch
    private boolean checkHorizontalWord(String word, IndexedCharacter ic, Letter branch)
    {
        if (branch.getParent().getOrientation() == WordOrientation.HORIZONTAL)
            return false;
        
        assert(ic.getCharacter() == branch.getCharacter());
        
        Set<Letter> matchY = getAllLettersWithSameYCoordinate(branch.getCoordinate());
        
        int wordLeftMostIndex = branch.getCoordinate().getX() - ic.getIndex();
        int wordRightMostIndex = branch.getCoordinate().getX() + (word.length() - ic.getIndex()) - 1;
        
        for (Letter l : matchY)
        {
            int l_x_coord = l.getCoordinate().getX();
            
            // outside of word location - doesn't matter to us
            if (l_x_coord < wordLeftMostIndex || l_x_coord > wordRightMostIndex)
                continue;
            
            // if within word location and characters don't match - fail - can't branch here
            if (l.getCharacter() != word.charAt(l_x_coord - wordLeftMostIndex))
                return false;
        }
        
        return true;
    }
    
    // TODO: improve checking of branch
    private boolean checkVerticalWord(String word, IndexedCharacter ic, Letter branch)
    {
        if (branch.getParent().getOrientation() == WordOrientation.VERTICAL)
            return false;
        
        assert(ic.getCharacter() == branch.getCharacter());
        
        Set<Letter> matchX = getAllLettersWithSameXCoordinate(branch.getCoordinate());
        
        int wordHighestIndex = branch.getCoordinate().getY() + ic.getIndex();
        int wordLowestIndex = branch.getCoordinate().getY() - (word.length() - ic.getIndex()) + 1;
        
        for (Letter l : matchX)
        {
            int l_y_coord = l.getCoordinate().getY();
            
            // out of bounds - continue
            if (l_y_coord > wordHighestIndex || l_y_coord < wordLowestIndex)
                continue;
            
            if (word.charAt(wordHighestIndex - l_y_coord) != l.getCharacter())
                return false;
        }
        
        return true;
    }
    
    private Set<Letter> getAllLettersWithSameYCoordinate(Coordinate c)
    {
        Set<Letter> sameY = new HashSet<Letter>();
        
        for (Letter l : usedLetters)
            if (l.getCoordinate().getY() == c.getY())
                sameY.add(l);
        
        return sameY;
    }
    
    private Set<Letter> getAllLettersWithSameXCoordinate(Coordinate c)
    {
        Set<Letter> sameX = new HashSet<Letter>();
        
        for (Letter l : usedLetters)
            if (l.getCoordinate().getX() == c.getX())
                sameX.add(l);
        
        return sameX;
    }
    
    private int[] getCrosswordBounds()
    {
        Letter[] boundaryLetters = new Letter[4];
        
        if (usedLetters.isEmpty())
            return null;
        
        for (Letter l : usedLetters)
        {
            // if letter is above the current max y
            if (boundaryLetters[UP_INDEX] == null 
                    || boundaryLetters[UP_INDEX].getCoordinate().getY() < l.getCoordinate().getY())
                boundaryLetters[UP_INDEX] = l;
            
            // if letter is below current min y
            if (boundaryLetters[DOWN_INDEX] == null 
                    || boundaryLetters[DOWN_INDEX].getCoordinate().getY() > l.getCoordinate().getY())
                boundaryLetters[DOWN_INDEX] = l;
            
            // if letter is further in the -x dir
            if (boundaryLetters[LEFT_INDEX] == null 
                    || boundaryLetters[LEFT_INDEX].getCoordinate().getX() > l.getCoordinate().getX())
                boundaryLetters[LEFT_INDEX] = l;
            
            // if letter is further in the +x dir
            if (boundaryLetters[RIGHT_INDEX] == null 
                    || boundaryLetters[RIGHT_INDEX].getCoordinate().getX() < l.getCoordinate().getX())
                boundaryLetters[RIGHT_INDEX] = l;
        }
        
        int[] bounds = new int[4];
        bounds[LEFT_INDEX] = boundaryLetters[LEFT_INDEX].getCoordinate().getX();
        bounds[RIGHT_INDEX] = boundaryLetters[RIGHT_INDEX].getCoordinate().getX();
        bounds[UP_INDEX] = boundaryLetters[UP_INDEX].getCoordinate().getY();
        bounds[DOWN_INDEX] = boundaryLetters[DOWN_INDEX].getCoordinate().getY();
        
        return bounds;
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
