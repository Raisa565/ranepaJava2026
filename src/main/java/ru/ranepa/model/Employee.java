package ru.ranera.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public final class Employee {
    private final Long id;
    private final String name;
    private final String position;
    private final double salary;
    private final LocalDate hireDate;

    private static final double MIN_SALARY = 0.0;
    private static final int MIN_NAME_LENGTH = 2;

    public Employee(Long id, String name, String position, double salary, LocalDate hireDate) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        if (name.length() < MIN_NAME_LENGTH) {
            throw new IllegalArgumentException("Name must be at least " + MIN_NAME_LENGTH + " characters");
        }
        if (salary < MIN_SALARY) {
            throw new IllegalArgumentException("Salary cannot be negative");
        }
        if (hireDate == null) {
            throw new IllegalArgumentException("Hire date cannot be null");
        }
        if (hireDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Hire date cannot be in the future");
        }
        
        this.id = id;
        this.name = name;
        this.position = position;
        this.salary = salary;
        this.hireDate = hireDate;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getPosition() { return position; }
    public double getSalary() { return salary; }
    public LocalDate getHireDate() { return hireDate; }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return String.format("ID: %d | %s | %s | %.2f | %s",
                id, name, position, salary, hireDate.format(formatter));
    }
}