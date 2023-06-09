package pt.ubi.wordle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;

public class SettingsController {

    private final String currDir = System.getProperty("user.dir");
    String filename = currDir + "/settings.txt";

    @FXML
    private Button btnExit;
    @FXML
    private HBox PT;
    @FXML
    private HBox EN;
    @FXML
    private HBox FR;

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
            handleExitButton();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
