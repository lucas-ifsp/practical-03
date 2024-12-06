package br.ifsp.services;

import br.ifsp.persistence.EmployeeDAO;

import java.util.NoSuchElementException;
import java.util.Objects;

public class RemoveEmployeeService {
    private final EmployeeDAO employeeDAO;

    public RemoveEmployeeService(EmployeeDAO employeeDAO) {
        this.employeeDAO = employeeDAO;
    }

    public void remove(String id) {
        Objects.requireNonNull(id, "ID must not be null.");

        final EmployeeDTO toRemove = employeeDAO.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Employee not found."));

        employeeDAO.findAll().stream()
                .filter(e -> id.equals(e.inChargeId()))
                .forEach(e -> updateChild(e, toRemove.inChargeId()));

        employeeDAO.delete(id);
    }

    private void updateChild(EmployeeDTO child, String newInChargeId) {
        final EmployeeDTO updated = new EmployeeDTO(
                child.id(),
                child.name(),
                child.birthDate(),
                child.soldValue(),
                newInChargeId
        );
        employeeDAO.update(updated);
    }
}
