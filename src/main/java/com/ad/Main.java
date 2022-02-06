package com.ad;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("view/mainScene.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.getIcons().add(new Image(Main.class.getResourceAsStream("images/icon.png")));
        stage.setResizable(false);
        stage.show();
    }

    public void stop() throws IOException {
        System.exit(0);
    }

    public static void main(String[] args) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy HH_mm_ss");
        Date date = new Date();
        String folderName = formatter.format(date).toString();
        String dir = System.getProperty("user.home") + "\\Desktop\\feed_data\\" + folderName + "\\";
        Config.saveToDir = dir;
        new File(dir).mkdirs();
        launch(args);
    }

}