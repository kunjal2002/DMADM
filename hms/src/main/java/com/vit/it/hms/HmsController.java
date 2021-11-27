package com.vit.it.hms;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HmsController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("My first venture into JavaFX Application!");
    }
}