package com.example.JIO.SolrSearch.Rest;


import com.example.JIO.SolrSearch.Models.Employee;
import com.example.JIO.SolrSearch.Models.EmployeeDTO;
import com.example.JIO.SolrSearch.Models.Skills;
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
import com.example.JIO.SolrSearch.Models.User;

import javax.annotation.PostConstruct;
import java.util.*;


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

    @PostConstruct
    public void loadData() {
        employeeRepository.deleteAll();

        Employees = new ArrayList<>();
        List<String> skill_set1 = new ArrayList<>();
        skill_set1.add("Java INTERMEDIATE");
       /* skill_set1.add(Skills.builder().skill_name("Java").skill_level("ADVANCED").build());
        skill_set1.add(Skills.builder().skill_name("Cpp").skill_level("BEGINNER").build());*/
        Employee e = new Employee.MyBuilder().id(randomLongGenerator.getRandomLong()).name("keshav joshi").emailId("keshav@gmail.com").suggestion().skills(skill_set1).mybuild();
        Employees.add(e);

      /*  List<Skills> skill_set2 = new ArrayList<>();
        skill_set2.add(Skills.builder().skill_name("Java").skill_level("INTERMEDIATE").build());
        skill_set2.add(Skills.builder().skill_name("ElasticSearch").skill_level("BEGINNER").build());
        Employee e1 = new Employee.MyBuilder().id(randomLongGenerator.getRandomLong()).name("rishikesh mohanty").emailId("abc@gmail.com").suggestion().skills(skill_set2).process().mybuild();
        Employees.add(e1);

        List<Skills> skill_set3 = new ArrayList<>();
        skill_set3.add(Skills.builder().skill_name("machine learning").skill_level("INTERMEDIATE").build());
        skill_set3.add(Skills.builder().skill_name("ElasticSearch").skill_level("BEGINNER").build());
        Employee e2 = new Employee.MyBuilder().id(randomLongGenerator.getRandomLong()).name("abhijeet raj").emailId("edf@gmail.com").suggestion().skills(skill_set3).process().mybuild();
        Employees.add(e2);

        List<Skills> skill_set4 = new ArrayList<>();
        skill_set4.add(Skills.builder().skill_name("java-script").skill_level("INTERMEDIATE").build());
        skill_set4.add(Skills.builder().skill_name("ARTIFICIAL INTELLIGENCE").skill_level("ADVANCED").build());
        skill_set4.add(Skills.builder().skill_name("cpp").skill_level("ADVANCED").build());
        Employee e3 = new Employee.MyBuilder().id(randomLongGenerator.getRandomLong()).name("anup bhutada").emailId("abc@gmail.com").suggestion().skills(skill_set4).process().mybuild();
        Employees.add(e3);

        List<Skills> skill_set5 = new ArrayList<>();
        skill_set5.add(Skills.builder().skill_name("c").skill_level("INTERMEDIATE").build());
        skill_set5.add(Skills.builder().skill_name("ElasticSearch").skill_level("BEGINNER").build());
        Employee e4 = new Employee.MyBuilder().id(randomLongGenerator.getRandomLong()).name("satrajit nath").emailId("abc@gmail.com").suggestion().skills(skill_set5).process().mybuild();
        Employees.add(e4);

        List<Skills> skill_set6 = new ArrayList<>();
        skill_set6.add(Skills.builder().skill_name("Java").skill_level("INTERMEDIATE").build());
        skill_set6.add(Skills.builder().skill_name("Apache-Solr").skill_level("BEGINNER").build());
        skill_set6.add(Skills.builder().skill_name("Spring-boot").skill_level("BEGINNER").build());
        Employee e5 = new Employee.MyBuilder().id(randomLongGenerator.getRandomLong()).name("Rohit chauhan").emailId("abc@gmail.com").suggestion().skills(skill_set6).process().mybuild();
        Employees.add(e5);*/

        employeeRepository.saveAll(Employees);
    }

    @GetMapping("/employees")
    public Page<Employee> getEmployees() {
        return (Page) employeeRepository.findAll();
    }

    @GetMapping("/search/{employeeName}")
    public List<Employee> getEmployeeBySuggestion(@PathVariable String employeeName) {

        return employeeRepository.findBySuggestion(employeeName,new Sort(Sort.Direction.DESC , "weight"));
    }

    @GetMapping("/hit/{userName}")
    public ResponseEntity<?> increaseWeight(@PathVariable String userName) {

        Optional<Employee> emp = employeeRepository.findByUserName(userName);

        System.out.println("oooooooooooooooooooooooooooooooinside increasing");

        if (emp.isPresent()) {

            emp.get().setWeight(emp.get().getWeight() + 1);
            employeeRestService.update(emp.get());

            return ResponseEntity.status(HttpStatus.CREATED).build();
        }

            return ResponseEntity.status((HttpStatus.BAD_REQUEST)).build();
    }

    @RequestMapping(value = "/add/employeeData", consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public ResponseEntity<String> save(@RequestBody EmployeeDTO employeeDTO) {
        System.out.print(employeeDTO);
        System.out.print("kkkkkkk");


        boolean status = employeeRestService.save(employeeDTO);

        if (status)
            return ResponseEntity.status(HttpStatus.CREATED).build();
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

    }

    @PutMapping("/update/employeeData")
    public Employee update(@RequestBody Employee employee) {
        Employee new_employee = employeeRestService.update(employee);

        if (new_employee == null) {
            return null; ////////////////////// error return................
        } else
            return new_employee;
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
        System.out.print(word);
        System.out.print(query);

        QueryResponse response = solrClient.query(query);


        SuggesterResponse suggesterResponse = response.getSuggesterResponse();
        Map<String, List<String>> suggestedTerm = suggesterResponse.getSuggestedTerms();

        List<String> suggestions = suggestedTerm.get("mySuggester");

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> " + suggestions);

        return suggestions;

    }

    @GetMapping("/remove")
    public void removeAll() {
        employeeRepository.deleteAll();
    }

}
