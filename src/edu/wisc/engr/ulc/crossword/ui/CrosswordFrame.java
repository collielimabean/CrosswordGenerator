package edu.wisc.engr.ulc.crossword.ui;

import javax.swing.JFrame;

public class CrosswordFrame
{
    private static final int DEFAULT_HEIGHT = 500;
    private static final int DEFAULT_WIDTH = 500;
    
    private JFrame frame;
    
    public CrosswordFrame()
    {
        frame = new JFrame("ULC Crossword Generator");
        frame.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public void show()
    {
        frame.setVisible(true);
    }
}
