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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.scene.layout.Priority;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutionException;

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
    void initialize() {
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
            for (int index = 5; index < fileBuffer.size(); index++) {
                if (fileBuffer.get(index).isEmpty()) continue;
                System.out.println("checking " + index);
                line = fileBuffer.get(index);

                if (line.charAt(0) == '>') {
                    line = line.substring(1);
                }

                String[] values = line.split(";");

                instantiateProfileBox(values[0], values[1]);
            }
        } catch (IOException e) {
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
        profileBox.setId(String.valueOf(profileCounter));
        profileBox.setAlignment(Pos.CENTER);
        profileBox.setSpacing(50);
        profileBox.setPrefHeight(100);
        profileBox.setPrefWidth(200);
        profileBox.setStyle("-fx-background-color: #BBBBFF");
        int index = profileHolder.getChildren().size();
        profileBox.setSpacing(10);
        profileBox.setPadding(new Insets(20));
        profileHolder.getChildren().add(index, profileBox);

        currentProfileBox.setOnMouseClicked(selectProfileEvent);

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

        //Set Create
        Button create = new Button("Criar");
        create.setStyle(" -fx-background-color: #EEEEEE;" + " -fx-font-family: Georgia;" + " -fx-font-size: 14px;" + " -fx-font-weight: bold");
        create.setOnAction(submitProfilePrompt);
        vBox2.getChildren().add(create);

        //Set X
        Button remove = new Button("X");
        remove.setStyle(" -fx-background-color: #FF6962;" + " -fx-font-family: Georgia;" + " -fx-font-size: 14px;" + " -fx-font-weight: bold");
        remove.setOnAction(removeProfilePrompt);
        vBox2.getChildren().add(remove);

    }

    void instantiateProfileBox(String name, String circleId) {
        newProfileButton.setDisable(false);
        //Set ProfileBox
        profileCounter++;
        //TODO if (profileCounter >= 3) newProfileButton.setDisable(true);
        System.out.println("Profile " + profileCounter + ":: Name: " + name + " Id: " + circleId);
        HBox profileBox = new HBox();
        currentProfileBox = profileBox;
        profileBox.setAlignment(Pos.CENTER);
        HBox.setHgrow(profileBox, Priority.SOMETIMES);
        profileBox.setSpacing(50);
        profileBox.setPrefHeight(100);
        profileBox.setPrefWidth(200);
        profileBox.setStyle("-fx-background-color: #BBBBFF");
        int index = profileHolder.getChildren().size();
        profileBox.setSpacing(10);
        profileBox.setPadding(new Insets(20));
        profileHolder.getChildren().add(index, profileBox);

        currentProfileBox.setOnMouseClicked(selectProfileEvent);

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

        Button edit = new Button("Editar");
        edit.setStyle(" -fx-background-color: #EEEEEE;" + " -fx-font-family: Georgia;" + " -fx-font-size: 14px;" + " -fx-font-weight: bold");
        edit.setOnAction(editProfile);

        vBox2.getChildren().add(0, edit);

        //X
        Button remove = new Button("X");
        remove.setStyle(" -fx-background-color: #FF6962;" + " -fx-font-family: Georgia;" + " -fx-font-size: 14px;" + " -fx-font-weight: bold");
        remove.setOnAction(removeProfilePrompt);
        vBox2.getChildren().add(remove);

        Button removeButton = (Button) vBox2.getChildren().get(1);
        removeButton.setOnAction(removeProfile);

    }

    // Actions para editar/submeter/remover

    EventHandler<ActionEvent> removeProfile = actionEvent -> {

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

            Button sourceButton = (Button) actionEvent.getSource();
            VBox sourceBox = (VBox) sourceButton.getParent();
            currentProfileBox = (HBox) sourceBox.getParent();

            VBox vbox1 = (VBox) currentProfileBox.getChildren().get(1);

            Label nameLabel = (Label) vbox1.getChildren().get(1);
            String name = nameLabel.getText();

            int index = 0;
            for (int i = 0; i < fileBuffer.size(); i++) {
                if (fileBuffer.get(i).contains(name)) {
                    index = i;
                }
            }
            System.out.println("removing " + fileBuffer.get(index));
            fileBuffer.remove(index);

            FileWriter writeStream = new FileWriter(file);

            for (String lines : fileBuffer) {
                writeStream.write(lines);
                writeStream.write("\n");
            }
            writeStream.close();

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        profileHolder.getChildren().remove(currentProfileBox);
        profileCounter--;
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
        removeButton.setDisable(false);
        removeButton.setOnAction(removeProfile);

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

            String output = name + ";";

            Circle colorId = (Circle) currentProfileBox.getChildren().get(0);


            output = output + hexToRGBString(colorId.getFill().toString());

            output = output + ";0";

            FileWriter writeStream = new FileWriter(file);

            for (String lines : fileBuffer) {
                writeStream.write(lines);
                writeStream.write("\n");
            }
            writeStream.write(output);
            writeStream.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

    };

    EventHandler<ActionEvent> submitProfileEdit = actionEvent -> {

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
        removeButton.setDisable(false);

        //Volta a passar por todos os outros perfis e permite-os a editar
        for (int i = 0; i < profileHolder.getChildren().size(); i++) {
            HBox IProfile = (HBox) profileHolder.getChildren().get(i);
            VBox IVbox2 = (VBox) IProfile.getChildren().get(2);

            Button IEditButton = (Button) IVbox2.getChildren().get(0);
            if (IEditButton.getText().equals("Enviar")) continue;

            IEditButton.setDisable(false);
            Button IRemoveButton = (Button) IVbox2.getChildren().get(1);
            IRemoveButton.setDisable(false);
        }

        Circle colorId = (Circle) currentProfileBox.getChildren().get(0);

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
            //Encontra o perfil a modificar se tiverem iguais colorIds
            for (int i = 0; i < fileBuffer.size(); i++) {
                if (fileBuffer.get(i).contains(hexToRGBString(colorId.getFill().toString()))) {
                    index = i;
                }
            }

            String output = "";
            if (fileBuffer.get(index).contains(">")) {
                output = fileBuffer.get(index).substring(1);
            } else output = fileBuffer.get(index);

            output = fileBuffer.get(index);

            String[] values = output.split(";");

            output = name + ";" + values[1] + ";" + values[2];

            if (fileBuffer.get(index).contains(hexToRGBString(colorId.getFill().toString()))) {
                output = ">" + output;
            }

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

    };


    EventHandler<ActionEvent> editProfile = actionEvent -> {

        Button sourceButton = (Button) actionEvent.getSource();
        VBox sourceBox = (VBox) sourceButton.getParent();
        currentProfileBox = (HBox) sourceBox.getParent();

        //Impedir de eliminar enquanto edita
        VBox vbox2 = (VBox) currentProfileBox.getChildren().get(2);
        Button removeButton = (Button) vbox2.getChildren().get(1);
        removeButton.setDisable(true);

        Button submitButton = (Button) vbox2.getChildren().get(0);
        submitButton.setText("Enviar");

        //Impedir de editar outros enquanto edita um
        for (int i = 0; i < profileHolder.getChildren().size(); i++) {
            HBox IProfile = (HBox) profileHolder.getChildren().get(i);
            VBox IVbox2 = (VBox) IProfile.getChildren().get(2);

            Button IEditButton = (Button) IVbox2.getChildren().get(0);
            if (IEditButton.getText().equals("Enviar")) continue;

            IEditButton.setDisable(true);
            Button IRemoveButton = (Button) IVbox2.getChildren().get(1);
            IRemoveButton.setDisable(true);
        }

        submitButton.setOnAction(submitProfileEdit);
        VBox vbox1 = (VBox) currentProfileBox.getChildren().get(1);
        Label nameLabel = (Label) vbox1.getChildren().get(1);
        String name = nameLabel.getText();

        vbox1.getChildren().remove(1);
        TextField nameField = new TextField(name);

        nameField.setStyle(" -fx-font-family: Georgia;" + " -fx-font-size: 18px");
        vbox1.getChildren().add(nameField);
    };

    EventHandler<ActionEvent> removeProfilePrompt = actionEvent -> {
        profileHolder.getChildren().remove(currentProfileBox);
        profileCounter--;
        newProfileButton.setDisable(false);
    };

    EventHandler<MouseEvent> selectProfileEvent = mouseEvent -> {
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

            currentProfileBox = (HBox) mouseEvent.getSource();

            VBox vbox1 = (VBox) currentProfileBox.getChildren().get(1);
            Label nameLabel = (Label) vbox1.getChildren().get(1);

            String name = nameLabel.getText();

            int oldIndex = 0;
            int newIndex = 0;
            for (int i = 0; i < fileBuffer.size(); i++) {
                if (fileBuffer.get(i).contains(">")) {
                    oldIndex = i;
                } else if (fileBuffer.get(i).contains(name)) {
                    newIndex = i;
                }
            }
            //Retira a seleção de perfil
            if (oldIndex != 0) {
                String oldSelectedProfile = fileBuffer.get(oldIndex).substring(1);
                fileBuffer.set(oldIndex, oldSelectedProfile);
            }

            //Adiciona a seleção de perfil
            if (newIndex != 0 && !fileBuffer.get(5).isEmpty()) {
                String newSelectedProfile = ">" + fileBuffer.get(newIndex);
                fileBuffer.set(newIndex, newSelectedProfile);
            } else {
                String newSelectedProfile = ">" + fileBuffer.get(5);
                fileBuffer.set(5, newSelectedProfile);
            }

            FileWriter writeStream = new FileWriter(file);

            for (String lines : fileBuffer) {
                writeStream.write(lines);
                writeStream.write("\n");
            }
            writeStream.close();
            handleExitButton();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
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

    public static String hexToRGBString(String hexColor) {
        Color color = Color.valueOf(hexColor); // Create a Color object from the hex color

        int red = (int) (color.getRed() * 255);     // Extract red component and convert to int
        int green = (int) (color.getGreen() * 255); // Extract green component and convert to int
        int blue = (int) (color.getBlue() * 255);   // Extract blue component and convert to int

        return String.format("%02X%02X%02X", red, green, blue); // Format RGB values as a hex string
    }
}


