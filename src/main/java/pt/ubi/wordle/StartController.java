package pt.ubi.wordle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;

public class StartController {

    char difficulty;
    String filename = "settings.txt";

    @FXML
    private Button bntPlay;
    @FXML
    private Button bntProfile;

    @FXML
    void initialize() {
        initFile();
    }

    @FXML
    void handlePlayButton() throws IOException {
        //Escreve a dificuldade em ficheiro
        //writeDifficulty();

        Stage currentStage = (Stage) bntPlay.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("game-view.fxml"));
        Scene newScene = new Scene(fxmlLoader.load());
        currentStage.setTitle("Wordle Game");
        currentStage.setScene(newScene);
    }

    @FXML
    protected void handleSettingsButton() throws IOException {
        Stage currentStage = (Stage) bntPlay.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("settings-view.fxml"));
        Scene newScene = new Scene(fxmlLoader.load());
        currentStage.setTitle("Wordle Settings");
        currentStage.setScene(newScene);
    }

    @FXML
    protected void handleProfileButton() throws IOException {
        Stage currentStage = (Stage) bntProfile.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("profile-view.fxml"));
        Scene newScene = new Scene(fxmlLoader.load());
        currentStage.setTitle("Wordle Profile");
        currentStage.setScene(newScene);
    }

    @FXML
    protected void handleExitButton() throws IOException {
        Stage currentStage = (Stage) bntProfile.getScene().getWindow();
        currentStage.close();
    }

    //Selecionar a dificuldade com base no dropdown selecionado
    @FXML
    void selectDifficulty(ActionEvent event) {
        MenuItem item = (MenuItem) event.getSource();
        difficulty = item.getText().charAt(0);
        writeDifficulty();
        System.out.println("Changed difficulty to " + difficulty);
    }

    void initFile() {
        try {
            File file = new File(filename);

            if (file.createNewFile()) {
                System.out.println("Created file " + filename);
                FileWriter writer = new FileWriter(file);
                writer.write("pt\n5\nword\n0\n.");
                writer.close();
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    void writeDifficulty() {
        try {
            File file = new File(filename);

            FileReader readStream = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(readStream);

            ArrayList<char[]> fileBuffer = new ArrayList<>();
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                fileBuffer.add(line.toCharArray());
            }

            char[] value = {difficulty};
            fileBuffer.set(1, value);
            readStream.close();
            bufferedReader.close();

            FileWriter writeStream = new FileWriter(file);

            for (char[] chars : fileBuffer) {
                writeStream.write(String.valueOf(chars));
                writeStream.write("\n");
            }
            writeStream.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

}
