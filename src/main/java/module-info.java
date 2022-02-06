module com.ad {
    requires javafx.base;
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.web;
    requires javafx.swing;
    requires tess4j;
    requires java.datatransfer;
    requires org.slf4j.simple;

    opens com.ad.controllers;
    opens com.ad;
}