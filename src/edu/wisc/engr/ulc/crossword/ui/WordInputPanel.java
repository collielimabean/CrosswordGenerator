package edu.wisc.engr.ulc.crossword.ui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class WordInputPanel extends JPanel
{
    private static final long serialVersionUID = 1L;
    
    private WordInputListener listener;
    
    public WordInputPanel()
    {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        JLabel lbl = new JLabel("Select a file with a word on each line below:");
        lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        lbl.setHorizontalAlignment(JLabel.CENTER);
        
        JButton selector = new JButton("Open");
        selector.setAlignmentX(Component.CENTER_ALIGNMENT);
        selector.setHorizontalAlignment(JButton.CENTER);
        
        selector.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                JFileChooser jfc = new JFileChooser();
                if (jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
                {
                    File f = jfc.getSelectedFile();
                    try
                    {
                        Scanner scan = new Scanner(f);
                        List<String> words = new ArrayList<String>();
                        while (scan.hasNextLine())
                            words.add(scan.nextLine().toLowerCase().trim());
                        
                        if (listener != null)
                            listener.handleWordInput(words);
                        
                        scan.close();
                    }
                    catch (FileNotFoundException e1)
                    {
                        e1.printStackTrace();
                    }
                }
            }
        });
        
        this.add(lbl);
        this.add(selector);

    }
    
    public void setListener(WordInputListener wil)
    {
        this.listener = wil;
    }
}
