package br.ifsp.view;

import br.ifsp.controller.EmployeeController;
import br.ifsp.controller.ViewType;
import br.ifsp.model.Employee;
import br.ifsp.services.EmployeeDTO;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class EmployeeView {

    public void showAndWait(){
        showAndWait(null, ViewType.REGISTER);
    }

    public void showAndWait(EmployeeDTO employee, ViewType type) {
        FXMLLoader loader = new FXMLLoader();
        try {
            final Pane graph = loader.load(Objects.requireNonNull(getClass().getResource("employee.fxml")).openStream());
            Scene scene = new Scene(graph, 300, 400);

            EmployeeController controller = loader.getController();
            controller.setViewMode(type);

            if(employee != null) {
                controller.setData(employee);
            }

            Stage stage = new Stage();
            stage.setScene(scene);

            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
