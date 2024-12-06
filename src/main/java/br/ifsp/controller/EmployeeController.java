package br.ifsp.controller;

import br.ifsp.persistence.EmployeeDAO;
import br.ifsp.persistence.SqliteEmployeeDAO;
import br.ifsp.services.EmployeeDTO;
import br.ifsp.services.RegisterEmployeeService;
import br.ifsp.services.UpdateEmployeeService;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.List;

public class EmployeeController {

    @FXML
    private Button btnExecute;

    @FXML
    private ComboBox<String> cbInCharge;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtSoldValue;

    final EmployeeDAO dao = new SqliteEmployeeDAO();
    ViewType viewMode;


    @FXML
    public void initialize() {
        final List<String> inChargeEmployees = dao.findAll().stream().map(EmployeeDTO::id).toList();
        cbInCharge.setItems(FXCollections.observableList(inChargeEmployees));
    }

    @FXML
    void execute(ActionEvent event) {
        if(txtId.getText() == null || txtId.getText().isBlank()) return;
        EmployeeDTO dto = getData();
        switch (viewMode){
            case UPDATE -> {
                UpdateEmployeeService service = new UpdateEmployeeService(dao);
                service.update(dto);
            }
            case REGISTER -> {
                RegisterEmployeeService service = new RegisterEmployeeService(dao);
                service.register(dto);
            }
        }
        close();
    }

    private void close(){
        ((Stage)btnExecute.getScene().getWindow()).close();
    }

    private EmployeeDTO getData() {
        return new EmployeeDTO(
                txtId.getText(),
                txtName.getText(),
                datePicker.getValue(),
                Double.parseDouble(txtSoldValue.getText()),
                cbInCharge.getSelectionModel().getSelectedItem()
        );
    }

    public void setData(EmployeeDTO employee) {
        txtId.setText(employee.id());
        txtName.setText(employee.name());
        txtSoldValue.setText(String.valueOf(employee.soldValue()));
        datePicker.setValue(employee.birthDate());
        cbInCharge.getSelectionModel().select(employee.inChargeId());
    }

    public void setViewMode(ViewType type) {
        viewMode = type;
        if(type == ViewType.REGISTER) return;

        txtId.setDisable(true);
        if(type == ViewType.UPDATE){
            btnExecute.setText("Update");
            return;
        }
        txtSoldValue.setDisable(true);
        txtName.setDisable(true);
        cbInCharge.setDisable(true);
        datePicker.setDisable(true);
        btnExecute.setText("Close");
    }
}

