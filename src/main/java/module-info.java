module org.inventory_system {
    requires javafx.fxml;
    requires javafx.web;
    requires javafx.media;


    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.sql;
    requires jasperreports;
    requires org.burningwave.core;
    requires de.jensd.fx.glyphs.fontawesome;
    requires net.bytebuddy;
    requires password4j;

    opens org.inventory_system to javafx.fxml;
    exports org.inventory_system;
    exports org.inventory_system.entity;
    opens org.inventory_system.entity to javafx.fxml;
    exports org.inventory_system.config;
    opens org.inventory_system.config to javafx.fxml;
    exports org.inventory_system.app;
    opens org.inventory_system.app to javafx.fxml;
}