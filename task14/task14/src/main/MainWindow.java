package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainWindow extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/MainWindow.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Цепочка обязанностей");
        stage.show();
    }

    public void run() {
        launch();
    }
}
