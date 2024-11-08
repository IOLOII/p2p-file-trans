package org.example.p2p.p2pfiletransfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.p2p.p2pfiletransfx.controller.AppController;

import java.io.IOException;

public class HelloApplication extends Application {
    private App app;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view-copy.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 300, 560);
        AppController controller = fxmlLoader.<AppController>getController();

        stage.setMinHeight(560);
        stage.setMaxHeight(560);
        stage.setMinWidth(300);
        stage.setMaxWidth(300);
        stage.setTitle("p2p file trans");
        stage.setScene(scene);

        controller.setStage(stage);

        app = new App(controller,stage);
        controller.setApp(app);


        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}