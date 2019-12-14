package com.gmail.Flameaxio;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("app.fxml"));
        primaryStage.setTitle("Math app");
        primaryStage.getIcons().add(new Image("file:icon.png"));
        Scene scene = new Scene(root, 1200, 600);
        //scene.getStylesheets().add(getClass().getResource("IBMPLexSans-Regular.otf").toExternalForm());
        //scene.getStylesheets().add(getClass().getResource("stylesheet.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
