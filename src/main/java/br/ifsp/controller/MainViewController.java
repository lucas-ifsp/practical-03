package br.ifsp.controller;

import br.ifsp.persistence.EmployeeDAO;
import br.ifsp.persistence.InMemoryEmployeeDAO;
import br.ifsp.persistence.SqliteEmployeeDAO;
import br.ifsp.services.CommissionService;
import br.ifsp.services.EmployeeDTO;
import br.ifsp.services.RemoveEmployeeService;
import br.ifsp.view.EmployeeView;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;

import java.util.List;

public class MainViewController {
    @FXML private TableView<EmployeeDTO> tableView;
    @FXML private TableColumn<EmployeeDTO, String> cId;
    @FXML private TableColumn<EmployeeDTO, String> cName;
    @FXML private TableColumn<EmployeeDTO, String> cBirthDate;
    @FXML private TableColumn<EmployeeDTO, String> cSoldValue;
    @FXML private TableColumn<EmployeeDTO, String> cInChargeId;
    @FXML private Label lbCommission;

    private final ObservableList<EmployeeDTO> employees = FXCollections.observableArrayList();
    private final EmployeeDAO repo = new SqliteEmployeeDAO();

    @FXML
    private void initialize() {
        bindColumnsToDto();
        bindTableToData();
        loadData();
    }

    private void bindColumnsToDto() {
        cId.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().id()));
        cName.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().name()));
        cBirthDate.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().birthDate().toString()));
        cSoldValue.setCellValueFactory(e -> new SimpleStringProperty(String.valueOf(e.getValue().soldValue())));
        cInChargeId.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().inChargeId()));
    }

    private void bindTableToData() {
        tableView.setItems(employees);
    }

    private void loadData() {
        final List<EmployeeDTO> data = repo.findAll();
        employees.clear();
        employees.addAll(data);
    }

    @FXML
    void btnDelete(ActionEvent event) {
        final EmployeeDTO item = tableView.getSelectionModel().getSelectedItem();
        if(item == null) return;

        RemoveEmployeeService service = new RemoveEmployeeService(repo);
        service.remove(item.id());
        loadData();
    }

    @FXML
    void btnNew(ActionEvent event) {
        final EmployeeView employeeView = new EmployeeView();
        employeeView.showAndWait();
        loadData();
    }

    @FXML
    void btnUpdate(ActionEvent event) {
        final EmployeeDTO item = tableView.getSelectionModel().getSelectedItem();
        if(item == null) return;

        final EmployeeView employeeView = new EmployeeView();
        employeeView.showAndWait(item, ViewType.UPDATE);
        loadData();
    }

    @FXML
    void btnView(ActionEvent event) {
        final EmployeeDTO item = tableView.getSelectionModel().getSelectedItem();
        if(item == null) return;

        final EmployeeView employeeView = new EmployeeView();
        employeeView.showAndWait(item, ViewType.VIEW);
    }

    @FXML
    public void showCommission(MouseEvent mouseEvent) {
        final EmployeeDTO item = tableView.getSelectionModel().getSelectedItem();
        if(item == null) return;

        CommissionService service = new CommissionService(repo);
        final double commission = service.calculateCommission(item.id());
        lbCommission.setText(String.valueOf(commission));
    }
}
