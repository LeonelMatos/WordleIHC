package pt.ubi.wordle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.security.KeyException;

public class GameController {

    int inputFocused = 0;

    @FXML private Button bntExit;

    int difficulty = 4;

    String word = "java";

    @FXML
    private HBox gameBox;

    @FXML
    void initialize () {
        for (int i = 0; i < difficulty; i++) {
            TextField textField = new TextField();
            textField.setId(String.valueOf(i));
            textField.setStyle("-fx-background-color: #ACB0F2;" +
                    " -fx-font-family: Georgia;" +
                    " -fx-font-size: 25px;" +
                    " -fx-alignment: center;" +
                    " -fx-pref-width: 50px;" +
                    " -fx-pref-height: 50px;");
            textField.setOnKeyTyped(this::focusOnTextInput);
            gameBox.getChildren().add(textField);

        }
        gameBox.getChildren().get(0).requestFocus();
    }

    @FXML
    void handleExitButton() throws IOException {
        Stage currentStage = (Stage) bntExit.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("start-view.fxml"));
        Scene newScene = new Scene(fxmlLoader.load());
        currentStage.setTitle("Wordle Settings");
        currentStage.setScene(newScene);
    }

    @FXML
    void focusOnTextInput(KeyEvent event) {
        inputFocused++;
        if (inputFocused < difficulty)
            gameBox.getChildren().get(inputFocused).requestFocus();
    }
}
