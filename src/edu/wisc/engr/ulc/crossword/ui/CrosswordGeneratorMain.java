package edu.wisc.engr.ulc.crossword.ui;

import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.stage.Stage;

public class CrosswordGeneratorMain extends Application
{
    private Stage stage;
    private final double MINIMUM_WINDOW_WIDTH = 500;
    private final double MINIMUM_WINDOW_HEIGHT = 500;
    
    public static void main(String[] args)
    {
        Application.launch(CrosswordGeneratorMain.class, (String[]) null);
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
