package edu.wisc.engr.ulc.crossword.generator;

/**
 * The Letter class represents a letter in a crossword. It keeps
 * references to its parent word - (e.g. 'o' in "orange"), as well as a
 * nullable reference to a possible branch (e.g. if the word "echo" intersected
 * with "orange"). 
 * 
 * @author William Jen
 * @since 3/27/2015
 */
public class Letter
{
    private final char letter;
    private final Coordinate location;
    private final Word parent;
    private Word branch;
    
    /**
     * Constructs a new Letter instance with the specified underlying character,
     * parent Word, and coordinate.
     * @param letter the underlying character represented by the Letter instance
     * @param parent the parent word that the character belongs to
     * @param location the location of this letter relative to the origin (which is the first letter
     * of the first word inserted into the crossword.)
     */
    public Letter(final char letter, final Word parent, final Coordinate location)
    {
        if (parent == null || location == null)
            throw new IllegalArgumentException("Parent word or coordinate cannot be null.");
        
        this.letter = letter;
        this.parent = parent;
        this.location = location;
    }
    
    /**
     * Gets the underlying character.
     * @return the underlying letter
     */
    public char getCharacter()
    {
        return letter;
    }
    
    /**
     * Gets the location of this letter.
     * @return the coordinate of the Letter instance
     */
    public Coordinate getCoordinate()
    {
        return location;
    }
    
    /**
     * Gets the parent word of this letter
     * @return the parent word of the Letter instance
     */
    public Word getParent()
    {
        return parent;
    }
    
    /**
     * Gets the branch word of this Letter instance, if any.
     * @return the branch word of this Letter, null if none
     */
    public Word getBranch()
    {
        return branch;
    }
    
    /**
     * Sets the branch word of this Letter instance.
     * @param branch The branch word
     */
    public void setBranch(final Word branch)
    {
        this.branch = branch;
    }
    
    public boolean equals(Object object)
    {
        if (!(object instanceof Letter))
            return false;
        
        Letter l = (Letter) object;
        
        return l.getParent().equals(parent) 
                && letter == l.getCharacter()
                && l.getCoordinate().equals(location)
                && (branch == null) ? (branch == l.getBranch()) : (branch.equals(l.getBranch()));
    }
}
