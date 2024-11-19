package com.example.conwaysgame;

import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.DragEvent;
import javafx.scene.input.KeyCode;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;

public class ConwaysController {
    @FXML
    private Label welcomeText;

    public static int probabilty;


    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Conways Game of Life");
    }

    @FXML
    public void start(ActionEvent event) {
        event.consume();
        ConwaysApplication.showMenu = false;
    }

    @FXML
    private Slider chanceSlider;

    public void initialize() {
        chanceSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
           probabilty = newValue.intValue();
            System.out.println(probabilty+"%");
        });
    }
}