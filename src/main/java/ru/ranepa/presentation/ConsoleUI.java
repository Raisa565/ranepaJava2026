package ru.ranera.presentation;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import ru.ranera.model.Employee;
import ru.ranera.service.HRMService;

public class ConsoleUI {
    private final HRMService service;
    private final Scanner scanner;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    private static final int MENU_SHOW_ALL = 1;
    private static final int MENU_ADD_EMPLOYEE = 2;
    private static final int MENU_DELETE_EMPLOYEE = 3;
    private static final int MENU_FIND_BY_ID = 4;
    private static final int MENU_SHOW_STATS = 5;
    private static final int MENU_EXIT = 6;

    public ConsoleUI(HRMService service) {
        if (service == null) {
            throw new IllegalArgumentException("Service cannot be null");
        }
        this.service = service;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        while (true) {
            printMenu();
            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> showAllEmployees();
                case "2" -> addEmployee();
                case "3" -> deleteEmployee();
                case "4" -> findEmployeeById();
                case "5" -> showStatistics();
                case "6" -> {
                    System.out.println("Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void printMenu() {
        System.out.println("\n=== HRM System Menu ===");
        System.out.println(MENU_SHOW_ALL + ". Show all employees");
        System.out.println(MENU_ADD_EMPLOYEE + ". Add employee");
        System.out.println(MENU_DELETE_EMPLOYEE + ". Delete employee");
        System.out.println(MENU_FIND_BY_ID + ". Find employee by ID");
        System.out.println(MENU_SHOW_STATS + ". Show statistics");
        System.out.println(MENU_EXIT + ". Exit");
        System.out.print("Choose option: ");
    }

    private void showAllEmployees() {
        List<Employee> employees = service.getAllEmployees();
        if (employees.isEmpty()) {
            System.out.println("No employees found.");
            return;
        }
        System.out.println("\n=== Employee List ===");
        employees.forEach(System.out::println);
    }

    private void addEmployee() {
        try {
            System.out.print("Enter name: ");
            String name = scanner.nextLine();
            
            System.out.print("Enter position: ");
            String position = scanner.nextLine();
            
            System.out.print("Enter salary: ");
            double salary = Double.parseDouble(scanner.nextLine());
            
            System.out.print("Enter hire date (dd.MM.yyyy): ");
            LocalDate hireDate = LocalDate.parse(scanner.nextLine(), dateFormatter);
            
            Employee employee = service.addEmployee(name, position, salary, hireDate);
            System.out.println("Employee added successfully with ID: " + employee.getId());
            
        } catch (NumberFormatException e) {
            System.err.println("Error: Invalid salary format. Please enter a number.");
        } catch (DateTimeParseException e) {
            System.err.println("Error: Invalid date format. Use dd.MM.yyyy (example: 15.03.2024)");
        } catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private void deleteEmployee() {
        System.out.print("Enter employee ID to delete: ");
        try {
            Long id = Long.parseLong(scanner.nextLine());
            if (service.deleteEmployee(id)) {
                System.out.println("Employee with ID " + id + " deleted successfully.");
            } else {
                System.out.println("Employee with ID " + id + " not found.");
            }
        } catch (NumberFormatException e) {
            System.err.println("Error: Invalid ID format. Please enter a number.");
        }
    }

    private void findEmployeeById() {
        System.out.print("Enter employee ID: ");
        try {
            Long id = Long.parseLong(scanner.nextLine());
            Optional<Employee> employee = service.findById(id);
            if (employee.isPresent()) {
                System.out.println(employee.get());
            } else {
                System.out.println("Employee with ID " + id + " not found.");
            }
        } catch (NumberFormatException e) {
            System.err.println("Error: Invalid ID format. Please enter a number.");
        }
    }

    private void showStatistics() {
        double avgSalary = service.calculateAverageSalary();
        Optional<Employee> topPaid = service.findTopPaidEmployee();

        System.out.println("\n=== Statistics ===");
        System.out.printf("Average salary: %.2f%n", avgSalary);
        
        if (topPaid.isPresent()) {
            System.out.println("Top paid employee: " + topPaid.get());
        } else {
            System.out.println("No employees in the system.");
        }
    }
}