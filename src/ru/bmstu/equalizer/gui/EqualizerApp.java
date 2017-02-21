/*
 * Copyright (C) Natalia Selyuto 2016 
 * Bauman Moscow State Technical University
 * IU3 
 */
package ru.bmstu.equalizer.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Natalia Selyuto 
 */
public class EqualizerApp extends Application {
     
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        
        Scene scene = new Scene(root);
         
        stage.setScene(scene);
        stage.show();
    }
    
     
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
