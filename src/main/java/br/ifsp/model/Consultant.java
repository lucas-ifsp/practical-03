package br.ifsp.model;

import java.time.LocalDate;
import java.util.*;

public final class Consultant extends Employee {
    private final Set<Employee> subordinates;

    public Consultant(String id, String name, LocalDate birthdate, double salesAmount, Consultant employeeInCharge) {
        super(id, name, birthdate, salesAmount, employeeInCharge);
        this.subordinates = new LinkedHashSet<>();
    }

    @Override
    public double getCommission() {
        return  subordinates.stream()
                .mapToDouble(Employee::getCommission)
                .map(value -> value * 0.3)
                .reduce(getSoldValue() * 0.15, Double::sum);
    }

    public void addSubordinate(Employee employee) {
        Objects.requireNonNull(employee, "Employee must not be null!");
        subordinates.removeIf(e -> e.getId().equals(employee.getId()));
        subordinates.add(employee);
    }
}
