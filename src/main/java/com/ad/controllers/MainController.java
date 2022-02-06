package com.ad.controllers;

import java.io.IOException;

import com.ad.Config;
import com.ad.analyse.Analyse;
import com.ad.bot.Odyssey;
import com.ad.utils.Logger;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import net.sourceforge.tess4j.TesseractException;
import javafx.scene.image.WritableImage;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.awt.image.BufferedImage;

public class MainController {

    private WebEngine engine = null;

    private Analyse analyse;

    private String linkedInUrl = Config.URL;

    private enum AutomationState {
        Running,
        Stopped
    }

    private Odyssey odyssey;

    private Logger logger;

    private AutomationState state = AutomationState.Stopped;

    private Draggable draggable = new Draggable();

    @FXML
    private WebView browser;

    @FXML
    private Button start_automation;

    @FXML
    private Rectangle input_canvas;

    @FXML
    private Circle bottom_resize;

    @FXML
    private Circle top_resize;

    @FXML
    private TextField canvasHeight;

    @FXML
    private TextField canvasWidth;

    @FXML
    private TextArea lookup_words;

    @FXML
    private TextField max_long_sleep_timer;

    @FXML
    private TextField max_small_sleep_timer;

    @FXML
    private TextField min_small_sleep_timer;

    @FXML
    private TextField min_scroll_depth;

    @FXML
    private TextField max_scroll_depth;

    @FXML
    private TextField min_long_sleep_timer;

    @FXML
    private TextArea automation_logs;

    @FXML
    private TextField website_url;

    @FXML
    public void initialize() {
        lookup_words.setWrapText(true);
        canvasWidth.setText(Config.canvasWidth + "");
        canvasHeight.setText(Config.canvasHeight + "");
        canvasWidth.setDisable(true);
        canvasHeight.setDisable(true);
        website_url.setDisable(true);
        website_url.setText(Config.URL);
        min_scroll_depth.setText(Config.minScrollDepth + "");
        max_scroll_depth.setText(Config.maxScrollDepth + "");
        min_small_sleep_timer.setText(Config.minShortTimeSleep + "");
        max_small_sleep_timer.setText(Config.maxShortTimeSleep + "");
        min_long_sleep_timer.setText(Config.minLongTimeSleep + "");
        max_long_sleep_timer.setText(Config.maxLongTimeSleep + "");
        String lookupWordsCombined = "";
        for (String i : Config.lookupWords) {
            lookupWordsCombined += i + ", ";
        }
        lookup_words.setText(lookupWordsCombined);

        engine = browser.getEngine();
        analyse = new Analyse();
        engine.load(linkedInUrl);
        input_canvas.setWidth(Config.canvasWidth);
        input_canvas.setHeight(Config.canvasHeight);
        // canvasX = (int) input_canvas.getLayoutX();
        // canvasY = (int) input_canvas.getLayoutY();
        // canvasWidth = (int) input_canvas.getWidth();
        // canvasHeight = (int) input_canvas.getHeight();
        // odyssey = new Odyssey(Config.Depth, Config.canvasJump);
        // input_canvas.setManaged(false);
        // input_canvas.layoutXProperty().bind(top_resize.layoutXProperty());
        // input_canvas.layoutYProperty().bind(top_resize.layoutYProperty());
        // input_canvas.widthProperty().bind(bottom_resize.layoutXProperty());
        // input_canvas.heightProperty().bind(bottom_resize.layoutYProperty());
        // draggable.make(top_resize);
        // draggable.make(bottom_resize);
        draggable.make(input_canvas);

        logger = new Logger();

        // logs
        automation_logs.setEditable(false);

    }

    @FXML
    void saveConfiguration(ActionEvent event) throws NumberFormatException {
        Config.canvasWidth = Integer.valueOf(canvasWidth.getText());
        Config.canvasHeight = Integer.valueOf(canvasHeight.getText());
        Config.minScrollDepth = Integer.valueOf(min_scroll_depth.getText());
        Config.maxScrollDepth = Integer.valueOf(max_scroll_depth.getText());
        Config.maxShortTimeSleep = Integer.valueOf(max_small_sleep_timer.getText());
        Config.minShortTimeSleep = Integer.valueOf(min_small_sleep_timer.getText());
        Config.maxLongTimeSleep = Integer.valueOf(max_long_sleep_timer.getText());
        Config.minLongTimeSleep = Integer.valueOf(min_long_sleep_timer.getText());
        Config.URL = website_url.getText();
        String ab = lookup_words.getText();
        Config.addToLookupWords(ab);
    }

    @FXML
    void startAutomation(ActionEvent event) throws IllegalArgumentException, IOException {
        if (state == AutomationState.Stopped) {
            state = AutomationState.Running;
            odyssey = new Odyssey(Config.Depth(), Config.canvasJump);
            logger = new Logger();
            this.startLogsWorker();
            start_automation.setText("Stop Automation");
            this.startWorker(event);
        } else {
            state = AutomationState.Stopped;
            start_automation.setText("Start Automation");
            this.stopWorker();
        }
    }

    public String webViewUrl() {
        return linkedInUrl;
    }

    public void startWorker(ActionEvent event) {
        ChangeListener<String> listener = new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                String result = newValue.toString();
                System.out.println(result);
                if (result.startsWith("scroll")) {
                    String[] a = result.split(":");
                    int height = Integer.valueOf(a[1]);
                    engine.executeScript("window.scrollTo(0, " + height + ")");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                    // Capture image
                    Node node = (Node) event.getSource();
                    WritableImage image = node.getScene().snapshot(null);
                    int sceneWidth = (int) node.getScene().getWidth();
                    int sceneHeight = (int) node.getScene().getHeight();
                    BufferedImage bf = new BufferedImage(sceneWidth, sceneHeight,
                            BufferedImage.TYPE_INT_RGB);
                    SwingFXUtils.fromFXImage(image, bf);

                    try {
                        analyse.start(bf, (int) input_canvas.getLayoutX(), (int) input_canvas.getLayoutY(),
                                (int) input_canvas.getWidth(),
                                (int) input_canvas.getHeight());
                    } catch (TesseractException | IOException e) {
                        e.printStackTrace();
                    }

                } else {
                    engine.reload();
                    engine.executeScript("document.body.scrollTop = 0");
                }
            }
        };
        odyssey.messageProperty().addListener(listener);
        Thread thread = new Thread(odyssey);
        thread.setDaemon(true);
        thread.start();
    }

    public void stopWorker() {
        odyssey.cancel();
        logger.cancel();
    }

    public void startLogsWorker() {
        ChangeListener<String> listener = new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                String result = newValue.toString();
                automation_logs.appendText(result + "\n");
            }
        };
        logger.messageProperty().addListener(listener);
        Thread loggerThread = new Thread(logger);
        loggerThread.setDaemon(true);
        loggerThread.start();
    }
}
