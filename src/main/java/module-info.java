module pt.ubi.wordle {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens pt.ubi.wordle to javafx.fxml;
    exports pt.ubi.wordle;
}