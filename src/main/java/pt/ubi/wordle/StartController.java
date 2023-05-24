package pt.ubi.wordle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public class StartController {

    @FXML private Button bntPlay;
    @FXML private Button bntProfile;

    @FXML
    void handlePlayButton() throws IOException {
        Stage currentStage = (Stage) bntPlay.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("game-view.fxml"));
        Scene newScene = new Scene(fxmlLoader.load());
        currentStage.setTitle("Wordle Game");
        currentStage.setScene(newScene);
    }

    @FXML protected void handleSettingsButton() throws IOException {
        Stage currentStage = (Stage) bntPlay.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("settings-view.fxml"));
        Scene newScene = new Scene(fxmlLoader.load());
        currentStage.setTitle("Wordle Settings");
        currentStage.setScene(newScene);
    }

    @FXML protected void handleProfileButton() throws IOException {
        Stage currentStage = (Stage) bntProfile.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("profile-view.fxml"));
        Scene newScene = new Scene(fxmlLoader.load());
        currentStage.setTitle("Wordle Profile");
        currentStage.setScene(newScene);
    }
}
