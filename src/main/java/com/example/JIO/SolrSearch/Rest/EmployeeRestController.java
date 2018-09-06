package com.example.JIO.SolrSearch.Rest;


import com.example.JIO.SolrSearch.Modals.Employee;
import com.example.JIO.SolrSearch.Modals.Skills;
import com.example.JIO.SolrSearch.Repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
        List<Skills> skill_set1 = new ArrayList<>();
        skill_set1.add(Skills.builder().skill_name("Java").skill_level("ADVANCED").build());
        skill_set1.add(Skills.builder().skill_name("Cpp").skill_level("BEGINNER").build());
        Employee e = Employee.builder().firstName("keshav").lastName("joshi").empId("111").emailId("keshav@gmail.com").skills(skill_set1).build();
        e.setFlattenedSkills(skill_set1);
        Employees.add(e);

        List<Skills> skill_set2 = new ArrayList<>();
        skill_set2.add(Skills.builder().skill_name("Java").skill_level("INTERMEDIATE").build());
        skill_set2.add(Skills.builder().skill_name("ElasticSearch").skill_level("BEGINNER").build());
        Employee e1 = Employee.builder().firstName("rishikesh").lastName("mohanty").empId("222").emailId("abc@gmail.com").skills(skill_set2).build();
        Employees.add(e1);

        employeeRepository.saveAll(Employees);
    }

    @GetMapping("/employees")
    public Page<Employee> getEmployees()
    {
        return (Page)employeeRepository.findAll();
    }

    @GetMapping("/search/{employeeName}")
    public Page<Employee> getEmployeeByfirstName(@PathVariable String employeeName)
    {
        return employeeRepository.findByFirstName( employeeName, new PageRequest(0,5));
    }

    @GetMapping("/{employeeSkills}")
    public Page<Employee> getEmployeeByskills(@PathVariable String employeeSkills)
    {

        /*if(employeeSkills.equals("ADVANCED") || employeeSkills.equals("BEGINNER") || employeeSkills.equals("INTERMEDIATE"))
        {
            return employeeRepository.findBySkillsEndingWith(employeeSkills , new PageRequest(0,5));
        }
        else
            return employeeRepository.findBySkillsStartingWith(employeeSkills , new PageRequest(0,5));*/


        return employeeRepository.findByFlattenedSkills(employeeSkills , new PageRequest(0 , 5));

    }

    @GetMapping("/remove")
    public void removeAll()
    {
        employeeRepository.deleteAll();
    }

}
