package edu.wisc.engr.ulc.crossword.generator;

/**
 * The Word class represents a word in a crossword puzzle.
 * 
 * @author William Jen
 * @since 3/27/2015
 */
public class Word
{
    private final Letter[] letters;
    private final WordOrientation orientation;
    private final String word;
    
    /**
     * Creates a new Word instance with the specified underlying word, coordinate of the first letter,
     * and orientation.
     * @param word the underlying word
     * @param initial the coordinate of the first letter
     * @param orientation the orientation of the word 
     * @throws IllegalArgumentException if word is null or empty
     */
    public Word(final String word, final Coordinate initial, final WordOrientation orientation)
    {
        if (word == null || word.length() == 0)
            throw new IllegalArgumentException();
        
        this.word = word;
        this.orientation = orientation;
        this.letters = new Letter[word.length()];
        
        // initialize each letter
        for (int i = 0; i < letters.length; i++)
        {
            int curX = initial.getX();
            int curY = initial.getY();
            
            // vertical words grow downward (-y axis)
            if (orientation == WordOrientation.VERTICAL)
                curY -= i;
            
            // whereas horizontal to the right (+x axis)
            else
                curX += i;
            
            Coordinate current = new Coordinate(curX, curY);
            
            letters[i] = new Letter(word.charAt(i), this, current);
        }
    }
    
    /**
     * Gets the underlying Letter nodes of the word.
     * @return the letter nodes
     */
    public Letter[] getLetters()
    {
        return letters;
    }
    
    public void setBranchWord(final int index, final Word word)
    {
        if (index < 0 || index >= letters.length || word == null)
            throw new IllegalArgumentException();
        
        letters[index].setBranch(word);
    }
    
    /**
     * Gets the orientation of the word.
     * @return The orientation of the word
     */
    public WordOrientation getOrientation()
    {
        return orientation;
    }
    
    /**
     * Gets the length of the word.
     * @return the word length
     */
    public int getLength()
    {
        return letters.length;
    }

    public String toString()
    {
        return word;
    }
}
