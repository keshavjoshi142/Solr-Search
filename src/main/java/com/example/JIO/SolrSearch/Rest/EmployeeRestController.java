package com.example.JIO.SolrSearch.Rest;


import com.example.JIO.SolrSearch.Models.Employee.Employee;
import com.example.JIO.SolrSearch.Models.Employee.EmployeeResquest;
import com.example.JIO.SolrSearch.Repository.EmployeeRepository;
import com.example.JIO.SolrSearch.Service.EmployeeRestService;
import com.example.JIO.SolrSearch.Service.RandomLongGenerator;
import com.example.JIO.SolrSearch.Service.UserService;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.SuggesterResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


@RestController
@RequestMapping("/api")
public class EmployeeRestController {

    private List<Employee> Employees;

    @Autowired
    SolrClient solrClient;

    @Autowired
    private UserService userService;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeRestService employeeRestService;

    private RandomLongGenerator randomLongGenerator = new RandomLongGenerator(new ArrayList<Long>());

    private static final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    @PostConstruct
    public void loadData() {
        //employeeRepository.deleteAll();
    }


        @GetMapping("/employees")
    public Page<Employee> getEmployees() {
        return (Page) employeeRepository.findAll();
    }



    @GetMapping("/search/{searchTerm}")
    public ResponseEntity<?> getEmployeeBySuggestion(@PathVariable String searchTerm) {

        if(Objects.isNull(searchTerm)) {

            logger.log(Level.WARNING , "Invalid Request");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(employeeRepository.findBySuggestion(searchTerm,new Sort(Sort.Direction.DESC , "weight")));

    }



    @GetMapping("/hit/{userName}")
    public ResponseEntity<?> increaseWeight(@PathVariable String userName) {
        if(Objects.isNull(userName)) {

            logger.log(Level.WARNING , "Invalid Request");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        Optional<Employee> emp = employeeRepository.findByUserName(userName);

        if (emp.isPresent()) {

            emp.get().setWeight(emp.get().getWeight() + 1);
            employeeRestService.update(emp.get());

            return ResponseEntity.status(HttpStatus.CREATED).build();
        }

            return ResponseEntity.status((HttpStatus.BAD_REQUEST)).build();
    }



    @RequestMapping(value = "/add/employeeData", consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public ResponseEntity<String> save(@RequestBody EmployeeResquest employeeResquest) {

        if(Objects.isNull(employeeResquest)) {

            logger.log(Level.WARNING , "Invalid Resquest");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        }

        boolean status = employeeRestService.save(employeeResquest);

        if(status) {

            logger.log(Level.INFO , "Employee data is added successfully");
            return ResponseEntity.status(HttpStatus.CREATED).build();

        } else {

            logger.log(Level.WARNING , "Cannot able to save Employee data");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }


    }

    @PutMapping("/update/employeeData")
    public ResponseEntity<?> update(@RequestBody Employee employee) {

        if(Objects.isNull(employee)) {

            logger.log(Level.WARNING , "Invalid Resquest");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        }

        Employee new_employee = employeeRestService.update(employee);

        if (new_employee == null) {

            logger.log(Level.WARNING , "Employee is present for update");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        } else {
            return ResponseEntity.ok(new_employee);
        }

    }

    @GetMapping("/employee/{username}")
    public ResponseEntity<?> getEmployeeWithUsername(@PathVariable String username) {

        System.out.println("in get");
        if(Objects.isNull(username)) {

            logger.log(Level.WARNING , "Invalid Resquest");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        }

        Optional<Employee> employee = employeeRepository.findByUserName(username);

        if(employee.isPresent()) {
            logger.log(Level.INFO , "Employee is successfully returned");
            return ResponseEntity.ok(employee);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }

    @GetMapping("/delete/{id}")
    public boolean delete(@PathVariable String id) {
        Optional<Employee> emp = employeeRepository.findById(id);

        if (emp.isPresent()) {
            employeeRepository.delete(employeeRepository.findById(id).get());
            return true;
        } else
            return false;
    }


    @GetMapping("/{employeeSkills}")
    public Page<Employee> getEmployeeByskills(@PathVariable String employeeSkills) {
        return employeeRepository.findByFlattenedSkills(employeeSkills, new PageRequest(0, 5));

    }


    @GetMapping("autocomplete/{word}")
    public List<String> SkillsSuggester(@PathVariable String word) throws Exception {
        SolrQuery query = new SolrQuery();
        query.setRequestHandler("/Employee_data/suggest");
        query.set("suggest", "true");
        query.set("suggest.build", "true");
        query.set("suggest.dictionary", "mySuggester");


        query.set("suggest.q", word);
        QueryResponse response = solrClient.query(query);

        SuggesterResponse suggesterResponse = response.getSuggesterResponse();
        Map<String, List<String>> suggestedTerm = suggesterResponse.getSuggestedTerms();

        List<String> suggestions = suggestedTerm.get("mySuggester");

        return suggestions;

    }

    @GetMapping("/remove")
    public void removeAll() {
        employeeRepository.deleteAll();
    }

}
