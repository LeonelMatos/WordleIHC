package pt.ubi.wordle;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class SettingsController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}
