module com.example.lesalonproject {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

//    requires org.xerial.sqlitejdbc;
    requires org.slf4j;

    requires jakarta.xml.bind;
//    requires jakarta.jws;
    requires jakarta.xml.ws;
    requires v20;
    opens com.example.lesalonproject.service to jakarta.xml.bind, jakarta.xml.ws;

    opens com.example.lesalonproject to javafx.fxml;
    exports com.example.lesalonproject;
}