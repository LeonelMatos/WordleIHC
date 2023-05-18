package pt.ubi.wordle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Random;

public class StartController {

    @FXML private Button bntPlay;
    @FXML private Button bntSetting;
    @FXML private Button bntProfile;

    @FXML
    void handlePlayButton() throws IOException {
        Stage currentStage = (Stage) bntPlay.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("game-view.fxml"));
        Scene newScene = new Scene(fxmlLoader.load(), currentStage.getWidth(), currentStage.getHeight());
        currentStage.setTitle("Wordle Game");
        currentStage.setScene(newScene);
    }

    @FXML protected void handleSettingsButton() throws IOException {
        Stage currentStage = (Stage) bntPlay.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("settings-view.fxml"));
        Scene newScene = new Scene(fxmlLoader.load(), currentStage.getWidth(), currentStage.getHeight());
        currentStage.setTitle("Wordle Settings");
        currentStage.setScene(newScene);
    }

    @FXML protected void handleProfileButton() throws IOException {
        Stage currentStage = (Stage) bntPlay.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("profile-view.fxml"));
        Scene newScene = new Scene(fxmlLoader.load(), currentStage.getWidth(), currentStage.getHeight());
        currentStage.setTitle("Wordle Profile");
        currentStage.setScene(newScene);
    }
}