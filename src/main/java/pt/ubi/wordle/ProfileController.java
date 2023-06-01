package pt.ubi.wordle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.scene.layout.Priority;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class ProfileController {

    private final String currDir = System.getProperty("user.dir");
    String filename = currDir + "/settings.txt";

    private int profileCounter = 0;

    @FXML
    private Button newProfileButton;
    @FXML
    private VBox profileHolder;
    @FXML
    private Button btnExit;

    //Usado para saber qual remover quando pedido
    @FXML
    private HBox currentProfileBox;

    @FXML
    void initialize () {
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

            for (int index = 5; index < fileBuffer.size()-1; index++) {
                if (fileBuffer.get(index).isEmpty()) continue;
                System.out.println("checking " + index);
                line = fileBuffer.get(index);

                if (line.charAt(0) == '>') {
                    line = line.substring(1);
                }

                String[] values = line.split("\\|");

                instantiateProfileBox(values[0], values[1]);
            }
        }
        catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @FXML
    void instantiateProfileBox() {
        newProfileButton.setDisable(true);
        //Set ProfileBox
        profileCounter++;
        HBox profileBox = new HBox();
        currentProfileBox = profileBox;
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
        title.setStyle(" -fx-font-family: Georgia;" + " -fx-font-size: 14px;" + " -fx-alignment: center;" + " -fx-font-weight: bold");
        vBox1.getChildren().add(title);

        TextField textField = new TextField();
        textField.setText("nome");
        textField.setStyle(" -fx-font-family: Georgia;" + " -fx-font-size: 18px");
        vBox1.getChildren().add(textField);

        //Set Edit
        Button create = new Button("Criar");
        create.setStyle(" -fx-background-color: #EEEEEE;" + " -fx-font-family: Georgia;" + " -fx-font-size: 14px;" + " -fx-font-weight: bold");
        create.setOnAction(submitProfilePrompt);
        vBox2.getChildren().add(create);

        //X Edit
        Button remove = new Button("X");
        remove.setStyle(" -fx-background-color: #FF6962;" + " -fx-font-family: Georgia;" + " -fx-font-size: 14px;" + " -fx-font-weight: bold");
        remove.setOnAction(removeProfilePrompt);
        vBox2.getChildren().add(remove);

    }

    void instantiateProfileBox(String name, String circleId) {
        System.out.println("Profile: " + name + circleId);
        newProfileButton.setDisable(true);
        //Set ProfileBox
        profileCounter++;
        HBox profileBox = new HBox();
        currentProfileBox = profileBox;
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
        Circle colorId = new Circle(30, Color.valueOf(circleId));
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
        title.setStyle(" -fx-font-family: Georgia;" + " -fx-font-size: 14px;" + " -fx-alignment: center;" + " -fx-font-weight: bold");
        vBox1.getChildren().add(title);

        //Set Name
        Label nameLabel = new Label(name);
        nameLabel.setStyle(" -fx-font-family: Georgia;" + " -fx-font-size: 24px;" + " -fx-alignment: center;" + " -fx-font-weight: bold");
        vBox1.getChildren().add(nameLabel);

        vBox2.getChildren().remove(0);
        Button create = new Button("Editar");
        create.setStyle(" -fx-background-color: #EEEEEE;" + " -fx-font-family: Georgia;" + " -fx-font-size: 14px;" + " -fx-font-weight: bold");
        vBox2.getChildren().add(0, create);

        Button removeButton = (Button) vBox2.getChildren().get(1);
        removeButton.setOnAction(removeProfile);
    }


    // Actions para editar/submeter/remover
    EventHandler<ActionEvent> removeProfile = actionEvent -> {
        profileHolder.getChildren().remove(currentProfileBox);
        profileCounter--;

        if (profileHolder.getChildren().size() < 3) newProfileButton.setDisable(false);
    };

    EventHandler<ActionEvent> submitProfilePrompt = actionEvent -> {
        //Verifica se existem demasiados perfis. 4+ pode partir a view
        if (profileHolder.getChildren().size() < 3) newProfileButton.setDisable(false);

        VBox vbox1 = (VBox) currentProfileBox.getChildren().get(1);
        TextField nameField = (TextField) vbox1.getChildren().get(1);
        String name = nameField.getText();

        vbox1.getChildren().remove(1);
        Label nameLabel = new Label(name);
        nameLabel.setStyle(" -fx-font-family: Georgia;" + " -fx-font-size: 24px;" + " -fx-alignment: center;" + " -fx-font-weight: bold");
        vbox1.getChildren().add(nameLabel);

        VBox vbox2 = (VBox) currentProfileBox.getChildren().get(2);
        vbox2.getChildren().remove(0);
        Button create = new Button("Editar");
        create.setStyle(" -fx-background-color: #EEEEEE;" + " -fx-font-family: Georgia;" + " -fx-font-size: 14px;" + " -fx-font-weight: bold");
        vbox2.getChildren().add(0, create);

        Button removeButton = (Button) vbox2.getChildren().get(1);
        removeButton.setOnAction(removeProfile);
    };

    EventHandler<ActionEvent> removeProfilePrompt = actionEvent -> {
        profileHolder.getChildren().remove(currentProfileBox);
        profileCounter--;
        newProfileButton.setDisable(false);
    };

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


