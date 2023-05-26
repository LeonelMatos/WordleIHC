package pt.ubi.wordle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.layout.Priority;

import java.io.IOException;
import java.util.Random;

public class ProfileController {

    private int profileCounter = 0;

    @FXML
    private VBox profileHolder;
    @FXML
    private Button btnExit;

    @FXML
    void instantiateProfileBox() {

        Font font = new Font("Georgia", 24);
        Font font2 = new Font("Georgia", 14);

        //Set ProfileBox
        profileCounter++;
        HBox profileBox = new HBox();
        profileBox.setAlignment(Pos.CENTER);
        profileBox.setSpacing(50);
        profileBox.setPrefHeight(100);
        profileBox.setPrefWidth(200);
        profileBox.setStyle("-fx-background-color: #BBBBFF");
        int index = profileHolder.getChildren().size();
        profileBox.setSpacing(10);
        profileBox.setPadding(new Insets(20));
        profileHolder.getChildren().add(index, profileBox);

        //Set ColorId

        Circle colorId = new Circle(30, Color.color(generateColor(), generateColor(), generateColor()));
        profileBox.getChildren().add(colorId);

        //Set VBox1
        VBox vBox1 = new VBox();
        vBox1.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(vBox1, Priority.SOMETIMES);
        profileBox.getChildren().add(vBox1);

        //Set VBox2
        VBox vBox2 = new VBox();
        vBox2.setAlignment(Pos.CENTER);
        vBox2.setSpacing(20);
        profileBox.getChildren().add(vBox2);

        //Set ProfileId
        Label title = new Label("Perfil " + profileCounter);
        title.setStyle(" -fx-font-family: Georgia;" +
                " -fx-font-size: 14px;" +
                " -fx-alignment: center;" +
                " -fx-font-weight: bold");
        title.setFont(font2);
        HBox.setHgrow(title, Priority.SOMETIMES);
        vBox1.getChildren().add(title);

        //Set Name
        Label name = new Label("Leonel");
        title.setStyle(" -fx-font-family: Georgia;" +
                " -fx-font-size: 24px;" +
                " -fx-alignment: center;" +
                " -fx-font-weight: bold");
        name.setFont(font);
        HBox.setHgrow(name, Priority.SOMETIMES);
        vBox1.getChildren().add(name);

        //Set Edit
        Button edit = new Button("Editar");
        edit.setStyle(" -fx-background-color: #EEEEEE;" +
                " -fx-font-family: Georgia;" +
                " -fx-font-size: 14px;" +
                " -fx-font-weight: bold");
        vBox2.getChildren().add(edit);

        //X Edit
        Button remove = new Button("X");
        remove.setStyle(" -fx-background-color: #FF6962;" +
                " -fx-font-family: Georgia;" +
                " -fx-font-size: 14px;" +
                " -fx-font-weight: bold");
        vBox2.getChildren().add(remove);
    }

    double generateColor() {
        Random random = new Random();
        double min = 0.4;  // Minimum component value
        double max = 0.8;  // Maximum component value

        double range = max - min;
        double value = random.nextDouble() * range + min;
        return Math.min(1.0, value);
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


