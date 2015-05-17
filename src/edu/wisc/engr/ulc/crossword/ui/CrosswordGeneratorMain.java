package edu.wisc.engr.ulc.crossword.ui;

import javax.swing.UIManager;


public class CrosswordGeneratorMain
{
    public static void main(String[] args) throws Exception
    {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        
        new CrosswordFrame().show();
    }
}
