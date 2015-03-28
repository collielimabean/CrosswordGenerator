package edu.wisc.engr.ulc.crossword.util;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

public class Bag<T>
{
    private List<T> elements;
    private Random elementPicker;
    
    public Bag()
    {
        elements = new ArrayList<T>();
        elementPicker = new Random();
    }
    
    public boolean isEmpty()
    {
        return elements.isEmpty();
    }
    
    public int size()
    {
        return elements.size();
    }
    
    public void add(T item)
    {
        if (item == null)
            return;
        
        elements.add(item);
    }
    
    public T remove()
    {
        if (elements.isEmpty())
            throw new NoSuchElementException();
        
        int index = elementPicker.nextInt(elements.size());
        
        return elements.remove(index);
    }
}
