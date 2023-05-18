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

    @FXML private Button start;

    @FXML protected void handlePlayButton() {
        Random r = new Random();
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        int width = (int) screenBounds.getWidth();
        int height = (int) screenBounds.getHeight();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("game-view.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.initStyle(StageStyle.DECORATED);
            stage.setTitle("Wordle Game");
            stage.setScene(new Scene(root));
            stage.setX(r.nextInt(width - (int) stage.getWidth()));
            stage.setY(r.nextInt(height - (int) stage.getHeight()));
            stage.show();
        } catch (IOException ignored) {}
    }

    @FXML protected void handleSettingsButton() {
        Random r = new Random();
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        int width = (int) screenBounds.getWidth();
        int height = (int) screenBounds.getHeight();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("settings-view.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.initStyle(StageStyle.DECORATED);
            stage.setTitle("Wordle Settings");
            stage.setScene(new Scene(root));
            stage.setX(r.nextInt(width - (int) stage.getWidth()));
            stage.setY(r.nextInt(height - (int) stage.getHeight()));
            stage.show();
        } catch (IOException ignored) {}
    }

    @FXML protected void handleProfileButton() {
        Random r = new Random();
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        int width = (int) screenBounds.getWidth();
        int height = (int) screenBounds.getHeight();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("profile-view.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.initStyle(StageStyle.DECORATED);
            stage.setTitle("Wordle Profile");
            stage.setScene(new Scene(root));
            stage.setX(r.nextInt(width - (int) stage.getWidth()));
            stage.setY(r.nextInt(height - (int) stage.getHeight()));
            stage.show();
        } catch (IOException ignored) {}
    }
}
