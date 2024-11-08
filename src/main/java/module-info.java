module org.example.p2p.p2pfiletransfx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;

    opens org.example.p2p.p2pfiletransfx to javafx.fxml;
    exports org.example.p2p.p2pfiletransfx;
    exports org.example.p2p.p2pfiletransfx.controller;
    opens org.example.p2p.p2pfiletransfx.controller to javafx.fxml;
}