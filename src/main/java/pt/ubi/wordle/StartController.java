package pt.ubi.wordle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.*;

public class StartController {

    char difficulty = '5';
    String filename = "settings.txt";

    @FXML private Button bntPlay;
    @FXML private Button bntProfile;

    @FXML
    void initialize() throws IOException {
        initFile();
    }

    @FXML
    void handlePlayButton() throws IOException {
        //Escreve a dificuldade em ficheiro
        writeDifficulty();

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

    //Selecionar a dificuldade com base no dropdown selecionado
    @FXML
    void selectDifficulty(ActionEvent event) {
        MenuItem item = (MenuItem) event.getSource();
        difficulty = item.getText().charAt(0);
        System.out.println("Changed difficulty to " + difficulty);
    }

    void initFile() throws IOException {
        try {
            File file = new File(filename);

            if (file.createNewFile()) {
                System.out.println("Created file " + filename);
            }
            else {
                System.out.println("File " + filename + " already exists");
            }

            FileWriter writer = new FileWriter(file);
            writer.write("pt\n5\n.");
            writer.close();
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    void writeDifficulty() throws IOException {
        try {
            ObjectInputStream readStream = new ObjectInputStream(new FileInputStream(filename));
            ObjectOutputStream writeStream = new ObjectOutputStream(new FileOutputStream(filename));

            char[] fileText = (char[]) readStream.readObject();

            fileText[4] = difficulty;

            writeStream.writeObject(String.valueOf(fileText));
            writeStream.flush();
        }
        catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

}
