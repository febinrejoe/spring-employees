package com.example.api.employee;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@RestController
public class EmployeeController {
    private EmployeeRepository repository;
    private SampleConfig sampleConfig;

    public EmployeeController(EmployeeRepository repository, SampleConfig sampleConfig) {
        this.repository = repository;
        this.sampleConfig = sampleConfig;
    }

    @PostConstruct
    public void init(){
        if(sampleConfig.isInitialize()){
            Employee one = new Employee();
            one.setFirstName(sampleConfig.getFirstNames()[0]);
            one.setLastName(sampleConfig.getLastNames()[0]);
            one.setEmail(sampleConfig.getEmails()[0]);
            repository.save(one);
            Employee two = new Employee();
            two.setFirstName(sampleConfig.getFirstNames()[1]);
            two.setLastName(sampleConfig.getLastNames()[1]);
            two.setEmail(sampleConfig.getEmails()[1]);
            repository.save(two);
        }
    }

    @GetMapping("/api/employees")
    public ResponseEntity<List<Employee>> list() {
        List<Employee> response = new ArrayList<>();
        repository.findAll().forEach(response::add);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/api/employees/{id}")
    public ResponseEntity<Employee> getEmployee(@PathVariable(value = "id") final String id) {
        return new ResponseEntity<>(repository.findOne(id), HttpStatus.OK);
    }

    @PostMapping("/api/employees")
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        return new ResponseEntity<>(repository.save(employee), HttpStatus.CREATED);
    }

    @PutMapping("/api/employees/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable(value = "id") final String id, @RequestBody Employee employee) {
        employee.setId(id);
        return new ResponseEntity<>(repository.save(employee), HttpStatus.OK);
    }

    @DeleteMapping("/api/employees/{id}")
    public ResponseEntity<Void> delete(@PathVariable(value = "id") final String id) {
        repository.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}