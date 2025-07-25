package ivancroce.u2_w2_d5_corporate_agency.controllers;

import ivancroce.u2_w2_d5_corporate_agency.entities.Employee;
import ivancroce.u2_w2_d5_corporate_agency.exceptions.exceptions.ValidationException;
import ivancroce.u2_w2_d5_corporate_agency.payloads.NewEmployeeDTO;
import ivancroce.u2_w2_d5_corporate_agency.payloads.NewEmployeeRespDTO;
import ivancroce.u2_w2_d5_corporate_agency.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    // 1. GET http://localhost:3001/employees
    @GetMapping
    public List<Employee> getEmployees() {
        return this.employeeService.findAll();
    }

    // 2. POST http://localhost:3001/employees (+ payload)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NewEmployeeRespDTO createEmployee(@RequestBody @Validated NewEmployeeDTO payload, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            validationResult.getAllErrors().forEach(System.out::println);
            throw new ValidationException(validationResult.getFieldErrors().stream().map(fieldError -> fieldError.getDefaultMessage()).toList());
        } else {
            Employee newEmployee = this.employeeService.saveEmployee(payload);
            return new NewEmployeeRespDTO(newEmployee.getId());
        }
    }

    // 3. GET http://localhost:3001/employees/{/employeeId}
    @GetMapping("/{employeeId}")
    public Employee getEmployeeById(@PathVariable UUID employeeId) {
        return this.employeeService.findById(employeeId);
    }

    // 4. PUT http://localhost:3001/employees/{/employeeId} (+ payload)
    @PutMapping("/{employeeId}")
    public Employee findEmployeeByIdAndUpdate(@PathVariable UUID employeeId, @RequestBody NewEmployeeDTO payload) {
        return this.employeeService.findByIdAndUpdate(employeeId, payload);
    }

    // 5. DELETE http://localhost:3001/employees/{/employeeId}
    @DeleteMapping("/{employeeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findEmployeeByIdAndDelete(@PathVariable UUID employeeId) {
        this.employeeService.findByIdAndDelete(employeeId);
    }

}
