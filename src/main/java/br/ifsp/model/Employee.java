package br.ifsp.model;

import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;

import static java.util.FormatProcessor.FMT;

public abstract sealed class Employee permits Consultant, Reseller {
    private final String id;
    private String name;
    private LocalDate birthDate;
    private double soldValue;
    private Consultant consultantInCharge;

    public Employee(String id, String name, LocalDate birthDate, double soldValue, Consultant consultantInCharge) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.soldValue = soldValue;
        this.consultantInCharge = consultantInCharge;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(id, employee.id);
    }

    @Override
    public String toString() {
        return FMT."[\{id}] \{name} | Birthday: \{birthDate} | Amount in sales: US$%.2f\{soldValue} | Commission: US$%.2f\{getCommission()}";
    }

    public abstract double getCommission();

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public int getAge() {
        return Period.between(birthDate, LocalDate.now()).getYears();
    }

    public double getSoldValue() {
        return soldValue;
    }

    public void setSoldValue(double soldValue) {
        this.soldValue = soldValue;
    }

    public Consultant getConsultantInCharge() {
        return consultantInCharge;
    }

    public void setConsultantInCharge(Consultant consultantInCharge) {
        this.consultantInCharge = consultantInCharge;
    }

    public void setSalesAmount(double salesAmount) {

    }
}
