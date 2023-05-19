package pt.ubi.wordle;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.security.Key;
import java.security.KeyException;

public class GameController {

    int inputFocused = 1;

    @FXML private Button bntExit;

    @FXML private VBox gameBox;

    int difficulty = 5;

    int attempts = 0;

    String word = "java";

    String entry = "";


    @FXML
    void initialize () {
        //Instanciar o gamebox
        HBox gBox = new HBox();
        gBox.setPrefHeight(80);
        gBox.setPrefWidth(200);
        gBox.setStyle("-fx-background-color: #BBBBBB");
        gBox.setAlignment(Pos.CENTER);
        gBox.setSpacing(10);
        gameBox.getChildren().add(gBox);

        //Instanciar o textfield
        for (int i = 0; i < difficulty; i++) {
            TextField textField = new TextField();
            textField.setId(String.valueOf(i));
            textField.setStyle("-fx-background-color: #ACB0F2;" +
                    " -fx-font-family: Georgia;" +
                    " -fx-font-size: 25px;" +
                    " -fx-alignment: center;" +
                    " -fx-pref-width: 50px;" +
                    " -fx-pref-height: 50px;");
            textField.setOnKeyTyped(this::textInputHandler);
            textField.setOnKeyPressed(event -> selectedTextField(event, gBox));
            gBox.getChildren().add(textField);

        }
        gBox.getChildren().get(1).requestFocus();
    }

    @FXML
    void handleExitButton() throws IOException {
        Stage currentStage = (Stage) bntExit.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("start-view.fxml"));
        Scene newScene = new Scene(fxmlLoader.load());
        currentStage.setTitle("Wordle Settings");
        currentStage.setScene(newScene);
    }

    // Move o cursor para a próxima letra no input
    // Guarda o input dado
    // Verifica tentativas
    @FXML
    void textInputHandler(KeyEvent event) {
        entry = "";
        TextField textField = (TextField) event.getSource();
        HBox gBox = (HBox) textField.getParent();

        textField.textProperty().setValue(textField.getText().toUpperCase());
        //Impede de escrever mais do que 1 char
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 1) {
                textField.setText(newValue.substring(0, 1)); // Limit to the first character
            }
        });


        if (isAllTextFieldsFilled(gBox) && attempts <= difficulty) {
            //falta verifica se a palavra existe
            for (int i = 0; i < difficulty; i++) {
                TextField currentField = (TextField) gBox.getChildren().get(i);
                String input = currentField.getText();
                System.out.println(input);
                entry = entry + input;
            }
            System.out.println(entry);

            //if (attempt.equals(word)) {
                instantiate_gBox();
                clear_gBox(gBox);
            //}
        }
    }

    void selectedTextField(KeyEvent event, HBox gbox) {
        //get current textfield
        //get current id
        //focus on id-1
        if (event.getCode() == KeyCode.BACK_SPACE) {
            TextField currentText = (TextField) event.getSource();
            currentText.clear();
            int currentId = Integer.parseInt(currentText.getId()) - 1;
            if (currentId == -1) return; //Não é necessário voltar no primeiro
            TextField newText = (TextField) gbox.getChildren().get(currentId);
            newText.requestFocus();
            newText.positionCaret(newText.getText().length());
        }
        else if (event.getCode().isLetterKey()) {
            TextField currentText = (TextField) event.getSource();
            int currentId = Integer.parseInt(currentText.getId()) + 1;
            HBox gBox = (HBox) currentText.getParent();
            int size = gBox.getChildren().size();
            if (currentId == size) return; //Não é necessário ir ao próximo
            TextField newText = (TextField) gbox.getChildren().get(currentId);
            newText.requestFocus();
            newText.positionCaret(newText.getText().length());
        }

    }

    void instantiate_gBox() {
        //Instanciar o gamebox
        HBox gBox = new HBox();
        gBox.setPrefHeight(80);
        gBox.setPrefWidth(200);
        gBox.setStyle("-fx-background-color: #BBBBBB");
        gBox.setAlignment(Pos.CENTER);
        gBox.setSpacing(10);
        int index = gameBox.getChildren().size() - 1;
        gameBox.getChildren().add(index, gBox);

        //Instanciar os labels
        for (int i = 0; i < difficulty; i++) {
            Label label = new Label();
            label.setId(String.valueOf(i));
            label.setStyle("-fx-background-color: #ACB0F2;" +
                    " -fx-font-family: Georgia;" +
                    " -fx-font-size: 25px;" +
                    " -fx-alignment: center;" +
                    " -fx-pref-width: 50px;" +
                    " -fx-pref-height: 50px;" +
                    " -fx-font-weight: bold");
            label.setText(String.valueOf(entry.charAt(i)));
            gBox.getChildren().add(label);

        }
    }

    private Boolean isAllTextFieldsFilled(HBox gBox) {
        for (int i = 0; i < difficulty; i++) {
            TextField currentField = (TextField) gBox.getChildren().get(i);
            if (currentField.getText().isEmpty())
                return false;
        }
        return true;
    }

    void clear_gBox (HBox gBox) {
        for (int i = 0; i < difficulty; i++) {
            TextField currentField = (TextField) gBox.getChildren().get(i);
            currentField.clear();
        }
        gBox.getChildren().get(0).requestFocus();
        inputFocused = 1;
    }
}
