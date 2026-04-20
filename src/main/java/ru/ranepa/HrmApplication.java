package ru.ranera;

import ru.ranera.presentation.ConsoleUI;
import ru.ranera.repository.EmployeeRepository;
import ru.ranera.service.HRMService;

public final class HrmApplication {
    private HrmApplication() {}
    
    public static void main(String[] args) {
        EmployeeRepository repository = new EmployeeRepository();
        HRMService service = new HRMService(repository);
        ConsoleUI ui = new ConsoleUI(service);
        ui.start();
    }
}