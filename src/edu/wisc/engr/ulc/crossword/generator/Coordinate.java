package edu.wisc.engr.ulc.crossword.generator;

/**
 * The Coordinate class represents a (x, y) point in 2D space.
 * Used to anchor crossword letters to a fixed reference frame.
 * 
 * @author William Jen
 * @since 3/27/2015
 */
public class Coordinate
{
    /**
     * The Coordinate pair denoting the origin.
     */
    public static final Coordinate ORIGIN = new Coordinate(0, 0);
    
    private final int x;
    private final int y;
    
    /**
     * Constructs a new Coordinate instance.
     * @param x The x value
     * @param y The y value
     */
    public Coordinate(final int x, final int y)
    {
        this.x = x;
        this.y = y;
    }
    
    /**
     * Gets the x coordinate.
     * @return the x coordinate
     */
    public int getX()
    {
        return x;
    }
    
    /**
     * Gets the y coordinate.
     * @return the y coordinate
     */
    public int getY()
    {
        return y;
    }
}
