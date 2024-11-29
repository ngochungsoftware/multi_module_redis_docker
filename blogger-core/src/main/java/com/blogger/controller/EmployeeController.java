package com.blogger.controller;

import com.blogger.data.dtos.EmployeeDTO;
import com.blogger.service.impl.EmployeeServiceImpl;
import io.reactivex.rxjava3.core.Single;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {

//    @Autowired
//    private EmployeeService employeeService;

    private final EmployeeServiceImpl employeeService;

    // Get all employees
    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
        try {
            List<EmployeeDTO> employees = employeeService.getAllEmployees();
            log.info("Retrieved all employees successfully.");
            return ResponseEntity.ok(employees);
        } catch (Exception e) {
            log.error("Error retrieving all employees: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Get employee by ID using RxJava Single
    @GetMapping("/{id}")
    public Single<EmployeeDTO> getEmployee(@PathVariable("id") Integer id) {
        return employeeService.getEmployeeById(id)
                .doOnSuccess(employee -> log.info("Retrieved employee with ID {}: {}", id, employee))
                .doOnError(e -> log.error("Error retrieving employee with ID {}: {}", id, e.getMessage(), e));
    }

    // Test method to demonstrate enriching an employee
//    @GetMapping("/test/{id}")
//    public Single<EmployeeDTO> testSingle(@PathVariable("id") Integer id) {
//        return employeeService.enrichEmployee(id)
//                .doOnSuccess(employee -> log.info("Successfully enriched employee with ID {}: {}", id, employee))
//                .doOnError(e -> log.error("Error enriching employee with ID {}: {}", id, e.getMessage(), e));
//    }

    // Create a new employee
    @PostMapping
    public ResponseEntity<EmployeeDTO> createEmployee(@RequestBody EmployeeDTO employeeDTO) {
        try {
            EmployeeDTO createdEmployee = employeeService.createEmployee(employeeDTO);
            log.info("Created employee: {}", createdEmployee);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdEmployee);
        } catch (Exception e) {
            log.error("Error creating employee: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Update an existing employee
    @PutMapping("/{id}")
    public ResponseEntity<Optional<EmployeeDTO>> updateEmployee(@PathVariable Integer id, @RequestBody EmployeeDTO employeeDTO) {
        try {
            Optional<EmployeeDTO> updatedEmployeeDTO = employeeService.updateEmployee(id, employeeDTO);
            if (updatedEmployeeDTO.isPresent()) {
                log.info("Updated employee with ID {}: {}", id, updatedEmployeeDTO);
                return ResponseEntity.ok(updatedEmployeeDTO);
            } else {
                log.warn("Employee with ID {} not found for update.", id);
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            log.error("Error updating employee with ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Delete an employee by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Integer id) {
        try {
            boolean deleted = employeeService.deleteEmployee(id);
            if (deleted) {
                log.info("Deleted employee with ID {}.", id);
                return ResponseEntity.noContent().build();
            } else {
                log.warn("Employee with ID {} not found for deletion.", id);
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            log.error("Error deleting employee with ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
