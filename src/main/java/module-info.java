module org.example.w4ind {
    requires javafx.controls;
    requires javafx.fxml;
    requires xstream;

    opens org.example.w4ind to javafx.fxml, xstream;
    exports org.example.w4ind to javafx.graphics;
}
