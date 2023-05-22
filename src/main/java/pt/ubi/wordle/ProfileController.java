package pt.ubi.wordle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class ProfileController {

    private int profileCounter = 0;

    @FXML
    VBox profileHolder;
    @FXML
    Button btnExit;

    @FXML
    void instantiateProfileBox() {
        profileCounter++;
        HBox profileBox = new HBox();
        profileBox.setAlignment(Pos.CENTER);
        profileBox.setSpacing(50);
        profileBox.setStyle("-fx-background-color: #AAAAAA");
        int index = profileHolder.getChildren().size();
        profileHolder.getChildren().add(index, profileBox);


        Label title = new Label("Perfil " + profileCounter);
        title.setStyle("-fx-background-color: #ACB0F2;" +
                " -fx-font-family: Georgia;" +
                " -fx-font-size: 18px;" +
                " -fx-alignment: center;" +
                " -fx-pref-width: 100px;" +
                " -fx-pref-height: 50px;" +
                " -fx-font-weight: bold");
        profileBox.getChildren().add(title);
    }

    @FXML
    void handleExitButton() throws IOException {
        Stage currentStage = (Stage) btnExit.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("start-view.fxml"));
        Scene newScene = new Scene(fxmlLoader.load());
        currentStage.setTitle("WordleIHC");
        currentStage.setScene(newScene);
    }

}


