module com.melissadata.personator {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;
    requires com.google.gson;
    requires jdk.crypto.ec;
    requires java.xml;

    opens com.melissadata.personator to javafx.fxml;
    opens com.melissadata.personator.model to javafx.fxml;
    opens com.melissadata.personator.view to javafx.fxml;
    exports com.melissadata.personator;
}
