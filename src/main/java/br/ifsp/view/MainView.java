package br.ifsp.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.Objects;

public class MainView extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        final Pane graph = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("main_view.fxml")));
        Scene scene = new Scene(graph, 700, 600);
        stage.setScene(scene);
        stage.setTitle("Employee Commission Manager - BES/ECM 2024");
        stage.show();
    }
}
