package pt.ubi.wordle;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class GameController {

    int difficulty = 4;

    String word = "java";
    @FXML
    private Label welcomeText;
    @FXML
    private GridPane grid;

    @FXML
    protected void addGridCell() {
        System.out.println("Check");


        for (int row = 0; row < difficulty; row++) {
            //grid.addRow(row, new Label("Row " + (row+1)));
            grid.getColumnConstraints().add(new ColumnConstraints(50));
        }
        for (int col = 0; col < difficulty; col++) {
            //grid.addColumn(col, new Label("Column " + (col+1)));
            grid.getRowConstraints().add(new RowConstraints(50));
        }
    }
}