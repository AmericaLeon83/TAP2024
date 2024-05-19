module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires aspose.pdf;
    opens com.example.demo to javafx.fxml;
    exports com.example.demo;
    exports com.example.demo.vistas;
    opens com.example.demo.vistas to javafx.fxml;
    exports com.example.demo.modelos;
    opens com.example.demo.modelos to javafx.fxml;

    opens com.example.demo.Componentes to javafx.base;

    requires java.sql;
    requires org.kordamp.bootstrapfx.core;
    requires mysql.connector.j;
    requires mariadb.java.client;
    requires java.desktop;
    //  opens com.example.demo.modelos;

}
