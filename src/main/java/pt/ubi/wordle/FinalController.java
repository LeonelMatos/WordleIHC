package pt.ubi.wordle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;


public class FinalController {

    private final String currDir = System.getProperty("user.dir");
    String filename = currDir + "/settings.txt";

    int points = Integer.parseInt(readPoints());

    @FXML
    Label success;
    @FXML
    Button btnExit;
    @FXML
    Label word;
    @FXML
    Label attempts;
    @FXML
    Label pointsLabel;
    @FXML
    VBox profileHolder;


    @FXML
    void handleExitButton() throws IOException {
        Stage currentStage = (Stage) btnExit.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("start-view.fxml"));
        Scene newScene = new Scene(fxmlLoader.load());
        currentStage.setTitle("WordleIHC");
        currentStage.setScene(newScene);
    }

    @FXML
    void initialize() throws Exception {
        String successMessage = "Bom Jogo!";
        int nAttempts;
        String emoji = "";
        String wordText = "";

        writePoints();

        wordText = "  " + Encryption.aesAlgorithm(readWord(), 2) + "  ";
        word.setText(wordText);

        nAttempts = Integer.parseInt(readAttempts()) + 1;

        if (nAttempts < wordText.length() - 4) {
            emoji = "※\\(^o^)/※";
        } else {
            successMessage = "Perdeste!";
            emoji = "(╥﹏╥)";
        }
        success.setText(successMessage);
        if (nAttempts == 1)
            attempts.setText(nAttempts + " tentativa\n" + emoji);
        else
            attempts.setText(nAttempts + " tentativas\n" + emoji);

        pointsLabel.setText(points + " pontos");
        int profileCount = getProfileCount();
        //Cria novos labels na scoreboard
        for (int i = 0; i < profileCount; i++) {
            Label profileLabel = new Label();
            profileLabel.setStyle("-fx-font-family: Georgia;" +
                    "-fx-font-size: 18px;" +
                    "-fx-alignment: center;" +
                    "-fx-font-weight: bold;" +
                    "-fx-text-fill: #DDDDDD");
            profileLabel.setText(readStats(i + 5));
            profileHolder.getChildren().add(profileLabel);

        }

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
        } catch (IOException e) {
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
        } catch (IOException e) {
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
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return "";
    }

    String readStats(int index) {
        try {

            File file = new File(filename);

            FileReader readStream = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(readStream);

            ArrayList<String> fileBuffer = new ArrayList<>();
            String line = "";

            for (int i = 0; i <= index; i++) {
                line = bufferedReader.readLine();
            }
            if (line.contains(">"))
                line = line.substring(1);

            String[] values = line.split(";");

            return "Perfil\t" + (index - 4) + "\t\t" + values[0] + "\t\t" + values[2];

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        return "";
    }

    int getProfileCount() {
        try {
            int count = 0;

            File file = new File(filename);

            FileReader readStream = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(readStream);

            ArrayList<String> fileBuffer = new ArrayList<>();
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                count++;
            }

            return count - 5;
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return 0;
    }

    void writePoints() {
        try {

            File file = new File(filename);

            FileReader readStream = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(readStream);

            ArrayList<String> fileBuffer = new ArrayList<>();
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                fileBuffer.add(line);
            }

            bufferedReader.close();
            readStream.close();

            int index = 0;
            for (int i = 5; i < fileBuffer.size(); i++) {
                if (fileBuffer.get(i).contains(">"))
                    index = i;
            }

            if (index == 0) return; //Guest

            String[] values = fileBuffer.get(index).split(";");

            String output = values[0] + ";" + values[1] + ";" + (Integer.parseInt(values[2]) + points);

            fileBuffer.set(index, output);

            FileWriter writeStream = new FileWriter(file);

            for (String lines : fileBuffer) {
                writeStream.write(lines);
                writeStream.write("\n");
            }
            writeStream.close();

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}

