package com.example.employeemgmt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        Optional<Employee> employee = employeeService.getEmployeeById(id);
        return employee.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Employee createEmployee(@RequestBody Employee employee) {
        return employeeService.saveEmployee(employee);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employeeDetails) {
        Optional<Employee> optionalEmployee = employeeService.getEmployeeById(id);
        if (!optionalEmployee.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Employee employee = optionalEmployee.get();
        employee.setName(employeeDetails.getName());
        employee.setEmail(employeeDetails.getEmail());
        employee.setDepartment(employeeDetails.getDepartment());
        return ResponseEntity.ok(employeeService.saveEmployee(employee));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        if (!employeeService.getEmployeeById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }
} 