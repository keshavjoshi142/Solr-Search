package com.example.JIO.SolrSearch.Rest;


import com.example.JIO.SolrSearch.Modals.Employee;
import com.example.JIO.SolrSearch.Repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api")
public class EmployeeRestController {

    private List<Employee> Employees;

    @Autowired
    private EmployeeRepository employeeRepository;

    @PostConstruct
    public void loadData()
    {
        Employees = new ArrayList<>();

        Employee e = Employee.builder().firstName("keshav").lastName("joshi").empId("111").emailId("keshav@gmail.com").build();
        Employees.add(e);
        Employee e1 = Employee.builder().firstName("rishikesh").lastName("mohanty").empId("222").emailId("abc@gmail.com").build();
        Employees.add(e1);

        employeeRepository.saveAll(Employees);
    }

    @GetMapping("/employees")
    public List<Employee> getEmployees()
    {
        return Employees;
    }

    @GetMapping("/{employeeName}")
    public Page<Employee> getEmployeeByfirstName(@PathVariable String employeeName)
    {
        return employeeRepository.findByFirstName( employeeName, new PageRequest(0,5));
    }
}