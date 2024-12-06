package br.ifsp.services;

import br.ifsp.exceptions.EntityAlreadyExists;
import br.ifsp.persistence.EmployeeDAO;

import java.util.NoSuchElementException;
import java.util.Objects;

public class RegisterEmployeeService {
    private final EmployeeDAO employeeDAO;

    public RegisterEmployeeService(EmployeeDAO employeeDAO) {
        this.employeeDAO = employeeDAO;
    }

    public void register(EmployeeDTO dto){
        Objects.requireNonNull(dto, "EmployeeDTO must not be null.");
        if(dto.id() == null) throw new IllegalArgumentException("EmployeeDTO must have an ID.");
        employeeDAO.findById(dto.id()).ifPresent(_ -> {throw new EntityAlreadyExists("Entity already exists. ");});

        if(dto.inChargeId() != null && employeeDAO.findById(dto.inChargeId()).isEmpty())
            throw new NoSuchElementException("There is no in change employee with this ID: " + dto.inChargeId());

        employeeDAO.save(dto);
    }
}
