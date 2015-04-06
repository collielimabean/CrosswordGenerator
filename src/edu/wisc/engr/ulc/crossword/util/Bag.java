package edu.wisc.engr.ulc.crossword.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

/**
 * The Bag data structure returns a random element on removal.
 * @author William Jen
 * @param <T> The type to be placed inside the Bag
 * @since 3/27/2015
 */
public class Bag<T> implements Iterable<T>
{
    private List<T> elements;
    private Random elementPicker;
    
    /**
     * Constructs a new Bag instance.
     */
    public Bag()
    {
        elements = new ArrayList<T>();
        elementPicker = new Random();
    }
    
    /**
     * Returns true if the bag has no elements.
     * @return true if empty, false otherwise
     */
    public boolean isEmpty()
    {
        return elements.isEmpty();
    }
    
    /**
     * Returns the number of elements in the Bag.
     * @return the number of elements
     */
    public int size()
    {
        return elements.size();
    }
    
    /**
     * Adds an item to the Bag.
     * @param item the item to be added
     */
    public void add(T item)
    {
        if (item == null)
            return;
        
        elements.add(item);
    }
    
    /**
     * Removes a random item in the Bag.
     * @return the removed item
     */
    public T remove()
    {
        if (elements.isEmpty())
            throw new NoSuchElementException();
        
        int index = elementPicker.nextInt(elements.size());
        
        return elements.remove(index);
    }
    
    /**
     * Removes a specific item in the Bag.
     * @param item the item to remove
     * @return true if successful, false otherwise
     */
    public boolean remove(T item)
    {
        return elements.remove(item);
    }

    @Override
    public Iterator<T> iterator()
    {
        List<T> copy = new ArrayList<T>(elements);
        Collections.shuffle(copy);
        return copy.iterator();
    }
}
