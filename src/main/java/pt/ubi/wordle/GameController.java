package pt.ubi.wordle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class GameController {

    private final String currDir = System.getProperty("user.dir");
    String filename = currDir + "/settings.txt";
    String yellowBox = "#FFFFCC";
    String greenBox = "#C3E6CB";
    String wordFilename = "";
    String language = "pt";
    String entry = "";
    String word;

    boolean gameWon = false;

    int inputFocused = 1;
    int difficulty = 5;
    int attempts = 1;

    String currentProfile = "";

    @FXML
    private ImageView currentLanguage;
    @FXML
    private Button btnProfile;
    @FXML
    private Button bntExit;
    @FXML
    private HBox inputBox;
    @FXML
    private VBox gameBox;
    @FXML
    private Label profileLabel;

    @FXML
    void initialize() {
        //Inicializar atributos
        difficulty = readDifficulty();
        language = readLanguage();
        word = readWord();
        writeWord();

        switch (language) {
            case "pt" -> currentLanguage.setImage(new Image("portugal.png"));
            case "en" -> currentLanguage.setImage(new Image("united-kingdom.png"));
            case "fr" -> currentLanguage.setImage(new Image("france.png"));
            default -> System.out.println("Image not found");
        }

        profileLabel.setText(readProfileName());

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
            textField.setStyle("-fx-background-color: #ACB0F2;" + " -fx-font-family: Georgia;" + " -fx-font-size: 25px;" + " -fx-alignment: center;" + " -fx-pref-width: 55px;" + " -fx-pref-height: 55px;" + " -fx-font-weight: bold");
            textField.setOnKeyPressed(this::textInputHandler);
            //textField.setOnKeyPressed(event -> selectedTextField(event, gBox));
            gBox.getChildren().add(textField);
        }
        gBox.getChildren().get(1).requestFocus();
    }

    void endOfGameWin() { //Fim do jogo ganho
        gameWon = true;
        System.out.println("FIM DE JOGO!");
        writeAttempts();
        try {
            handleFinalView();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @FXML
    protected void handleProfileButton() throws IOException {
        Stage currentStage = (Stage) btnProfile.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("profile-view.fxml"));
        Scene newScene = new Scene(fxmlLoader.load());
        currentStage.setTitle("Wordle Profile");
        currentStage.setScene(newScene);
    }

    @FXML
    void handleExitButton() throws IOException {
        Stage currentStage = (Stage) bntExit.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("start-view.fxml"));
        Scene newScene = new Scene(fxmlLoader.load());
        currentStage.setTitle("Wordle Settings");
        currentStage.setScene(newScene);
    }

    void handleFinalView() throws IOException {
        Stage currentStage = (Stage) bntExit.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("final-view.fxml"));
        Scene newScene = new Scene(fxmlLoader.load());
        currentStage.setTitle(word);
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
        inputBox = gBox;

        //textField.textProperty().setValue(textField.getText().toUpperCase());
        textField.setText(textField.getText().toUpperCase());

        //Impede de escrever mais do que 1 char
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 1) {
                textField.setText(newValue.substring(0, 1)); // Limit to the first character
            }
        });

        if (event.getCode() == KeyCode.BACK_SPACE) {
            textField.clear();  //Backspace limpa text field
            int currentId = Integer.parseInt(textField.getId());
            if (currentId == 0) return;
            TextField gotoText = (TextField) gBox.getChildren().get(currentId - 1);
            gotoText.requestFocus();
        } else if (!textField.getText().isEmpty()) {
            int currentId = Integer.parseInt(textField.getId());
            if (currentId == difficulty - 1) return;
            TextField gotoText = (TextField) gBox.getChildren().get(currentId + 1);
            focusText(gotoText);
        }

        if (isAllTextFieldsFilled(gBox) && attempts <= difficulty) {
            submitWord();
        }

    }

    @FXML
    void submitWord() {
        for (int i = 0; i < difficulty; i++) {
            TextField currentField = (TextField) inputBox.getChildren().get(i);
            String input = currentField.getText();
            entry = entry.concat(input);
        }
        System.out.println("Tentativa: " + entry);

        if (doesWordExist(entry.toLowerCase())) {
            instantiateInputBox();
            clearInputBox();
            attempts++;
        }
        entry = "";
    }

    void focusText(TextField gotoText) {
        gotoText.requestFocus();
    }

    void instantiateInputBox() {
        //Instanciar o gamebox
        int correctChars = 0;

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
            String entryChar = String.valueOf(entry.charAt(i));
            Label label = new Label();
            label.setId(String.valueOf(i));
            label.setStyle(" -fx-background-color: #ACB0F2;" + " -fx-font-family: Georgia;" + " -fx-font-size: 25px;" + " -fx-alignment: center;" + " -fx-pref-width: 50px;" + " -fx-pref-height: 50px;" + " -fx-font-weight: bold");
            label.setText(entryChar);

            if (word.contains(entryChar.toLowerCase())) {
                if (word.charAt(i) == entryChar.toLowerCase().charAt(0)) {

                    label.setStyle(" -fx-background-color: " + greenBox + ";" +
                            " -fx-font-family: Georgia;" +
                            " -fx-font-size: 25px;" +
                            " -fx-alignment: center;" +
                            " -fx-pref-width: 50px;" +
                            " -fx-pref-height: 50px;" +
                            " -fx-font-weight: bold");
                    correctChars++;
                } else {
                    label.setStyle(" -fx-background-color: " + yellowBox + ";" +
                            " -fx-font-family: Georgia;" +
                            " -fx-font-size: 25px;" +
                            " -fx-alignment: center;" +
                            " -fx-pref-width: 50px;" +
                            " -fx-pref-height: 50px;" +
                            " -fx-font-weight: bold");
                }
            }

            gBox.getChildren().add(label);

            if (correctChars == difficulty) {
                endOfGameWin();
            }
        }
    }

    private Boolean isAllTextFieldsFilled(HBox gBox) {
        for (int i = 0; i < difficulty; i++) {
            TextField currentField = (TextField) gBox.getChildren().get(i);
            if (currentField.getText().isEmpty()) return false;
        }
        return true;
    }

    void clearInputBox() {
        for (int i = 0; i < difficulty; i++) {
            TextField currentField = (TextField) inputBox.getChildren().get(i);
            currentField.clear();
        }
        inputBox.getChildren().get(0).requestFocus();
        inputFocused = 1;
    }

    int readDifficulty() {
        int value = 7;
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
            value = Integer.parseInt(String.valueOf(fileBuffer.get(1)[0]));
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return value; //Integer.parseInt(String.valueOf(value));
    }

    String readLanguage() {
        char[] language = {};
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

            language = new char[]{fileBuffer.get(0)[0], fileBuffer.get(0)[1]};
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return String.valueOf(language);
    }

    int countFileSize(String path) {
        try {
            int lineCount = 0;

            File wordFile = new File(currDir + "/wordlists/" + path);

            FileReader readStream = new FileReader(wordFile);
            BufferedReader bufferedReader = new BufferedReader(readStream);

            while (bufferedReader.readLine() != null) lineCount++;

            bufferedReader.close();
            readStream.close();

            return lineCount;
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return 0;
    }

    String readWord() {
        char[] wordFilenameArr = {'0', '-', '0', '0', '.', 't', 'x', 't'}; //ficheiro a abrir (linguagem+dificuldade)

        String word = "";

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

            //guarda linguagem
            wordFilenameArr[2] = fileBuffer.get(0)[0];
            wordFilenameArr[3] = fileBuffer.get(0)[1];

            //guarda dificuldade
            wordFilenameArr[0] = fileBuffer.get(1)[0];

            System.out.println("Abrir o ficheiro " + String.valueOf(wordFilenameArr));

            wordFilename = String.valueOf(wordFilenameArr);

            int lineCount = countFileSize(wordFilename);

            File wordFile = new File(currDir + "/wordlists/" + wordFilename);

            FileReader readStream2 = new FileReader(wordFile);
            BufferedReader bufferedReader2 = new BufferedReader(readStream2);

            Random random = new Random();
            int randomLine = random.nextInt(lineCount) + 1;

            for (int i = 0; i < randomLine; i++)
                bufferedReader2.readLine();

            word = Objects.requireNonNull(Encryption.aesAlgorithm(bufferedReader2.readLine(), 2)).toLowerCase();

            bufferedReader2.close();
            readStream2.close();

            System.out.println("A palavra é: " + word + " (linha " + randomLine + ")");

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return word;
    }

    String readProfileName() {
        String name = "";
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

            for (String s : fileBuffer) {
                if (s.contains(">")) {
                    name = s.substring(1).split(";")[0];
                }
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return name;
    }

    boolean doesWordExist(String word) {
        try {
            File file = new File(currDir + "/wordlists/" + wordFilename);

            FileReader readStream = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(readStream);

            String line;

            while ((line = Encryption.aesAlgorithm(bufferedReader.readLine(), 2)) != null) {
                if (line.equals(word)) {
                    System.out.println("A palavra " + word + " existe! (✿◠‿◠)");
                    bufferedReader.close();
                    readStream.close();
                    return true;
                }
            }
            System.out.println("A palavra " + "não existe (ง •̀_•́)ง");
            bufferedReader.close();
            readStream.close();

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return false;
    }

    void writeWord() {
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

            fileBuffer.set(2, word);

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

    void writeAttempts() {
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

            fileBuffer.set(3, String.valueOf(attempts));

            FileWriter writeStream = new FileWriter(file);

            for (String lines : fileBuffer) {
                writeStream.write(lines);
                writeStream.write("\n");
            }
            writeStream.close();
            System.out.println("Made " + attempts + " attempts (づ｡◕‿‿◕｡)づ");
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
