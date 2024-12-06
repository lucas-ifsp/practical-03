package br.ifsp.services;

import br.ifsp.model.Employee;
import br.ifsp.persistence.EmployeeDAO;

import java.util.NoSuchElementException;
import java.util.Objects;

public class UpdateEmployeeService {
    private final EmployeeDAO employeeDAO;

    public UpdateEmployeeService(EmployeeDAO employeeDAO) {
        this.employeeDAO = employeeDAO;
    }

    public void update(EmployeeDTO dto) {
        Objects.requireNonNull(dto, "EmployeeDTO must not be null.");
        if(dto.id() == null) throw new IllegalArgumentException("EmployeeDTO must have an ID.");

        employeeDAO.findById(dto.id()).orElseThrow(() -> new NoSuchElementException("EmployeeDTO not found."));
        if(dto.inChargeId() != null && employeeDAO.findById(dto.inChargeId()).isEmpty())
            throw new NoSuchElementException("There is no in change employee with this ID: " + dto.inChargeId());

        employeeDAO.update(dto);
    }
}
