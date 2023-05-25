package pt.ubi.wordle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;

public class SettingsController {

    String filename = "settings.txt";

    @FXML
    private Button btnExit;

    @FXML HBox PT;
    @FXML HBox EN;
    @FXML HBox FR;

    @FXML
    void handleExitButton() throws IOException {
        Stage currentStage = (Stage) btnExit.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("start-view.fxml"));
        Scene newScene = new Scene(fxmlLoader.load());
        currentStage.setTitle("WordleIHC");
        currentStage.setScene(newScene);
    }

    @FXML
    void selectLanguage(MouseEvent event) {
        String language;
        HBox selectedBox = (HBox) event.getSource();

        language = selectedBox.getId().toLowerCase();

        //selectedBox.setStyle("-fx-background-color: #CCCCCC"); Ponderar dar um feedback para a selecionada

        System.out.println("choose language " + language);

        try {
            File file = new File(filename);

            FileReader readStream = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(readStream);

            ArrayList<char[]> fileBuffer = new ArrayList<>();
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                fileBuffer.add(line.toCharArray());
            }
            bufferedReader.close();
            readStream.close();

            fileBuffer.set(0, new char[]{language.charAt(0), language.charAt(1)});

            FileWriter writeStream = new FileWriter(file);

            for (char[] chars : fileBuffer) {
                writeStream.write(String.valueOf(chars));
                writeStream.write("\n");
            }
            writeStream.close();
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
