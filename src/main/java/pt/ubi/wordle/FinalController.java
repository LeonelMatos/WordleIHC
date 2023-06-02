package pt.ubi.wordle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class FinalController {

    private final String currDir = System.getProperty("user.dir");
    String filename = currDir + "/settings.txt";

    @FXML
    Label success;
    @FXML
    Button btnExit;
    @FXML
    Label word;
    @FXML
    Label attempts;
    @FXML
    Label points;

    @FXML
    void handleExitButton() throws IOException {
        Stage currentStage = (Stage) btnExit.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("start-view.fxml"));
        Scene newScene = new Scene(fxmlLoader.load());
        currentStage.setTitle("WordleIHC");
        currentStage.setScene(newScene);
    }

    @FXML
    void initialize () {
        String successMessage = "Bom Jogo!";
        int nAttempts;
        String emoji = "";
        String wordText = "";

        wordText = "  " + readWord() + "  ";
        word.setText(wordText);

        nAttempts = Integer.parseInt(readAttempts());

        if (nAttempts < wordText.length() - 4) {
            emoji = "※\\(^o^)/※";
        }
        else  {
            successMessage = "Perdeste!";
            emoji = "(╥﹏╥)";
        }
        success.setText(successMessage);
        if (nAttempts == 1)
            attempts.setText(nAttempts + " tentativa\n" + emoji);
        else
            attempts.setText(nAttempts + " tentativas\n" + emoji);
    }

    String readWord() {
        try {

            File file = new File(filename);

            FileReader readStream = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(readStream);

            ArrayList<String> fileBuffer = new ArrayList<>();
            String line;

            for (int i = 0; i < 3; i++) {
                line = bufferedReader.readLine();
                fileBuffer.add(line);
            }
            bufferedReader.close();
            readStream.close();

            return fileBuffer.get(2);
        }
        catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return "";
    }

    String readAttempts() {
        try {

            File file = new File(filename);

            FileReader readStream = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(readStream);

            ArrayList<String> fileBuffer = new ArrayList<>();
            String line;

            for (int i = 0; i < 4; i++) {
                line = bufferedReader.readLine();
                fileBuffer.add(line);
            }
            bufferedReader.close();
            readStream.close();

            return fileBuffer.get(3);
        }
        catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return "";
    }

    String readPoints() {
        try {

            File file = new File(filename);

            FileReader readStream = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(readStream);

            ArrayList<String> fileBuffer = new ArrayList<>();
            String line;

            for (int i = 0; i < 5; i++) {
                line = bufferedReader.readLine();
                fileBuffer.add(line);
            }
            bufferedReader.close();
            readStream.close();

            return fileBuffer.get(4);
        }
        catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return "";
    }
}
