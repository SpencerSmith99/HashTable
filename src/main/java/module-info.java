module dictionary {
    requires javafx.controls;
    requires javafx.fxml;


    opens dictionary to javafx.fxml;
    exports dictionary;
}