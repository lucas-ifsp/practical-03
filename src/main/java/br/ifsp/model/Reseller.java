package br.ifsp.model;

import java.time.LocalDate;

public final class Reseller extends Employee {

    public Reseller(String id, String name, LocalDate birthdate, double salesAmount, Consultant employeeInCharge) {
        super(id, name, birthdate, salesAmount, employeeInCharge);
    }

    @Override
    public double getCommission() {
        return getSoldValue() * 0.15;
    }
}
