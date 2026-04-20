package ru.ranera.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import ru.ranera.model.Employee;

public class EmployeeRepository {
    private final Map<Long, Employee> storage = new HashMap<>();
    private Long currentId = 1L;

    public Employee save(Employee employee) {
        if (employee == null) {
            throw new IllegalArgumentException("Employee cannot be null");
        }
        
        Employee employeeToSave;
        if (employee.getId() == null) {
            employeeToSave = new Employee(currentId++, employee.getName(), 
                    employee.getPosition(), employee.getSalary(), employee.getHireDate());
        } else {
            employeeToSave = employee;
        }
        storage.put(employeeToSave.getId(), employeeToSave);
        return employeeToSave;
    }

    public List<Employee> findAll() {
        return new ArrayList<>(storage.values());
    }

    public Optional<Employee> findById(Long id) {
        if (id == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(storage.get(id));
    }

    public boolean delete(Long id) {
        if (id == null) {
            return false;
        }
        return storage.remove(id) != null;
    }

    public boolean isEmpty() {
        return storage.isEmpty();
    }
}