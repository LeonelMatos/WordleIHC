package pt.ubi.wordle;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;

import java.util.Random;

public class GameController {

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
                    " -fx-font-size: 34px;" +
                    " -fx-alignment: center;" +
                    " -fx-pref-width: 60px;" +
                    " -fx-pref-height: 60px;");

            gameBox.getChildren().add(textField);
        }
    }



}