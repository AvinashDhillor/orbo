package com.ad.analyse.notify;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.ad.Main;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Notifications {
    public static void notify(File file) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("view/popupScene.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        ImageView imageView = (ImageView) scene.lookup("#resulted_image");
        Image image = new Image(new FileInputStream(file));
        imageView.setImage(image);
        stage.setScene(scene);

        stage.initStyle(StageStyle.UNDECORATED);

        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

        stage.setX(primaryScreenBounds.getMinX() + primaryScreenBounds.getWidth() - 512);
        stage.setY(primaryScreenBounds.getMinY() + primaryScreenBounds.getHeight() - 288);
        stage.setWidth(512);
        stage.setHeight(228);
        stage.setAlwaysOnTop(true);
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.submit(() -> Platform.runLater(stage::show));
        executor.schedule(
                () -> Platform.runLater((stage::close)), 3, TimeUnit.SECONDS);
        ;
    }
}
