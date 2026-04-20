package ru.ranera.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import ru.ranera.model.Employee;
import ru.ranera.repository.EmployeeRepository;

public class HRMService {
    private final EmployeeRepository repository;

    public HRMService(EmployeeRepository repository) {
        if (repository == null) {
            throw new IllegalArgumentException("Repository cannot be null");
        }
        this.repository = repository;
    }

    public Employee addEmployee(String name, String position, double salary, LocalDate hireDate) {
        Employee employee = new Employee(null, name, position, salary, hireDate);
        return repository.save(employee);
    }

    public List<Employee> getAllEmployees() {
        return repository.findAll();
    }

    public Optional<Employee> findById(Long id) {
        return repository.findById(id);
    }

    public boolean deleteEmployee(Long id) {
        return repository.delete(id);
    }

    public double calculateAverageSalary() {
        List<Employee> employees = repository.findAll();
        if (employees.isEmpty()) {
            return 0.0;
        }
        return employees.stream()
                .mapToDouble(Employee::getSalary)
                .average()
                .orElse(0.0);
    }

    public Optional<Employee> findTopPaidEmployee() {
        return repository.findAll().stream()
                .max((e1, e2) -> Double.compare(e1.getSalary(), e2.getSalary()));
    }

    public List<Employee> filterByPosition(String position) {
        if (position == null || position.trim().isEmpty()) {
            return List.of();
        }
        return repository.findAll().stream()
                .filter(e -> e.getPosition().equalsIgnoreCase(position.trim()))
                .collect(Collectors.toList());
    }
}