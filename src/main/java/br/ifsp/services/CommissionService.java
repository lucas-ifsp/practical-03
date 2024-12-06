package br.ifsp.services;

import br.ifsp.model.Consultant;
import br.ifsp.model.Reseller;
import br.ifsp.persistence.EmployeeDAO;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

public class CommissionService {
    private final EmployeeDAO employeeDAO;

    public CommissionService(EmployeeDAO employeeDAO) {
        this.employeeDAO = employeeDAO;
    }

    public double calculateCommission(String id) {
        Objects.requireNonNull(id, "ID must not be null.");
        final EmployeeDTO result = employeeDAO.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Employee not found."));

        final List<EmployeeDTO> children = employeeDAO.findAll().stream()
                .filter(e -> id.equals(e.inChargeId()))
                .toList();

        if (children.isEmpty()) {
            return new Reseller(
                    result.id(),
                    result.name(),
                    result.birthDate(),
                    result.soldValue(),
                    null).getCommission();
        }

        return getWithChildren(result).getCommission();
    }

    private Consultant getWithChildren(EmployeeDTO inCharge) {
        final Consultant consultant = new Consultant(
                inCharge.id(),
                inCharge.name(),
                inCharge.birthDate(),
                inCharge.soldValue(),
                null);
        employeeDAO.findAll().stream()
                .filter(e -> inCharge.id().equals(e.inChargeId()))
                .map(this::getWithChildren)
                .forEach(consultant::addSubordinate);
        return consultant;
    }
}
