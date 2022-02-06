package com.ad.controllers;

import javafx.scene.Cursor;
import javafx.scene.Node;

public class Draggable {
    private double mouseX;
    private double mouseY;

    public void make(Node node) {

        node.setOnMousePressed(MouseEvent -> {
            mouseX = MouseEvent.getX();
            mouseY = MouseEvent.getY();
            node.setCursor(Cursor.OPEN_HAND);
        });

        node.setOnMouseDragged(MouseEvent -> {
            node.setLayoutX(MouseEvent.getSceneX() - mouseX);
            node.setLayoutY(MouseEvent.getSceneY() - mouseY);
        });
    }
}
