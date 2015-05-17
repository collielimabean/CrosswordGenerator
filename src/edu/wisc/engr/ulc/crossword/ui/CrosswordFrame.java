package edu.wisc.engr.ulc.crossword.ui;

import java.awt.BorderLayout;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Map;
import java.util.Stack;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import edu.wisc.engr.ulc.crossword.generator.Crossword;
import edu.wisc.engr.ulc.crossword.generator.LetterCipherFactory;
import edu.wisc.engr.ulc.crossword.util.CrosswordHTMLGenerator;

public class CrosswordFrame implements WordInputListener
{
    private static final int DEFAULT_HEIGHT = 500;
    private static final int DEFAULT_WIDTH = 500;
    
    private JFrame frame;
    private WordInputPanel wordInputPanel;
    
    public CrosswordFrame()
    {
        frame = new JFrame("ULC Crossword Generator");
        frame.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        wordInputPanel = new WordInputPanel();
        wordInputPanel.setListener(this);
        frame.add(wordInputPanel, BorderLayout.CENTER);
    }
    
    public void show()
    {
        frame.setVisible(true);
    }

    @Override
    public void handleWordInput(Collection<String> words)
    {
        Stack<String> s = new Stack<>();
        
        for (String word : words)
            s.push(word);
        
        Crossword c = new Crossword();
        c.addWordList(s);
        
        JFileChooser jfc = new JFileChooser();
        jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        
        if (jfc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION)
        {
            File dir = jfc.getSelectedFile();
            File answer = new File(dir, "key.html");
            File encrypted = new File(dir, "student.html");
            try
            {
                Map<Character, Integer> cipher = LetterCipherFactory.generate();
                PrintWriter pw = new PrintWriter(answer);
                pw.write(CrosswordHTMLGenerator.generateAnswerKey(c, cipher));
                pw.close();
                
                PrintWriter pw2 = new PrintWriter(encrypted);
                pw2.write(CrosswordHTMLGenerator.generateWorksheet(c, cipher));
                pw2.close();
            }
            catch (FileNotFoundException e)
            {
                e.printStackTrace();
            }
        }
    }
}
