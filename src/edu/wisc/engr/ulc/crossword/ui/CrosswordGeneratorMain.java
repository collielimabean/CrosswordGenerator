package edu.wisc.engr.ulc.crossword.ui;

import java.io.File;
import java.util.Scanner;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.stage.Stage;
import edu.wisc.engr.ulc.crossword.generator.Crossword;

public class CrosswordGeneratorMain extends Application
{
    private Stage stage;
    private final double MINIMUM_WINDOW_WIDTH = 500;
    private final double MINIMUM_WINDOW_HEIGHT = 500;
    
    public static void main(String[] args) throws Exception
    {
        // Application.launch(CrosswordGeneratorMain.class, (String[]) null);
        
        /* XXX: TEST CODE REMOVE */
        Scanner s = new Scanner(new File("names.txt"));
        Stack<String> names = new Stack<>();
        
        while (s.hasNextLine())
            names.add(s.nextLine());
        s.close();
        
        Crossword c = new Crossword();
        System.out.println("Adding: " + c.addWordList(names));
        System.out.println();
        c.printCharacterBuffer();
        /* END TEST CODE */
    }

    @Override
    public void start(Stage primaryStage)
    {
        try
        {
            stage = primaryStage;
            stage.setTitle("Crossword Generator");
            stage.setHeight(MINIMUM_WINDOW_HEIGHT);
            stage.setWidth(MINIMUM_WINDOW_WIDTH);
            stage.show();
        }
        
        catch (Exception e)
        {
            Logger.getLogger(CrosswordGeneratorMain.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
