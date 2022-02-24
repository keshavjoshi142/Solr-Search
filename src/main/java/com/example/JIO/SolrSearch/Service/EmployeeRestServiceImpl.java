package com.example.JIO.SolrSearch.Service;

import com.example.JIO.SolrSearch.Models.Employee.Education;
import com.example.JIO.SolrSearch.Models.Employee.Employee;
import com.example.JIO.SolrSearch.Models.Employee.EmployeeResquest;
import com.example.JIO.SolrSearch.Models.Employee.Projects;
import com.example.JIO.SolrSearch.Repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.JIO.SolrSearch.Models.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EmployeeRestServiceImpl implements EmployeeRestService{

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private UserService userService;

    private RandomLongGenerator randomLongGenerator = new RandomLongGenerator(new ArrayList<Long>());



    @Override
    public Employee update(Employee employee) {

        final Optional<Employee> retrievedEmployee = employeeRepository.findById(employee.getId());

        if(retrievedEmployee.isPresent()) {
            employeeRepository.delete(retrievedEmployee.get());

            retrievedEmployee.get().setId(employee.getId());
            retrievedEmployee.get().setName(employee.getName());
            retrievedEmployee.get().setUserName(employee.getUserName());
            retrievedEmployee.get().setEmailId(employee.getEmailId());
            retrievedEmployee.get().setPhoneNo(employee.getPhoneNo());
            retrievedEmployee.get().setSkills(employee.getSkills());
            retrievedEmployee.get().setInstName(employee.getInstName());
            retrievedEmployee.get().setDegree(employee.getDegree());
            retrievedEmployee.get().setEduFrom(employee.getEduFrom());
            retrievedEmployee.get().setEduTo(employee.getEduTo());
            retrievedEmployee.get().setProjectName(employee.getProjectName());
            retrievedEmployee.get().setProjFrom(employee.getProjFrom());
            retrievedEmployee.get().setProjTo(employee.getProjTo());
            retrievedEmployee.get().setDescription(employee.getDescription());
            retrievedEmployee.get().setWeight(employee.getWeight());
            retrievedEmployee.get().setSuggestion(new ArrayList<String>());

            employeeRepository.save(retrievedEmployee.get());

            return retrievedEmployee.get();
        } else {
            return null;
        }
    }


    public boolean save(EmployeeResquest employeeResquest) {

            List<String> instName = new ArrayList<String>();
            List<String> degree = new ArrayList<String>();
            List<String> eduFrom = new ArrayList<String>();
            List<String> eduTo = new ArrayList<String>();

            List<String> projectName = new ArrayList<String>();
            List<String> projFrom = new ArrayList<String>();
            List<String> projTo = new ArrayList<String>();
            List<String> description = new ArrayList<String>();

            List<Education> educationList = employeeResquest.getEducation();
            List<Projects> projectsList = employeeResquest.getProjects();

            for(Education education : educationList) {
                instName.add(education.getInstName());
                degree.add(education.getDegree());
                eduFrom.add(education.getFrom());
                eduTo.add(education.getTo());
            }

            for(Projects projects : projectsList) {
                projectName.add(projects.getProjectName());
                projFrom.add(projects.getFrom());
                projTo.add(projects.getTo());
                description.add(projects.getDescription());
            }

            Employee newEmployee = new Employee.MyBuilder().id(randomLongGenerator.getRandomLong()).name(employeeResquest.getName()).userName(employeeResquest.getUserName()).emailId(employeeResquest.getEmailId()).
                                        phoneNo(employeeResquest.getPhoneNo()).skills(employeeResquest.getSkills()).instName(instName).degree(degree).eduFrom(eduFrom).eduTo(eduTo).
                                        projectName(projectName).projFrom(projFrom).projTo(projTo).description(description).image(employeeResquest.getImage()).weight(Long.valueOf(0)).mybuild();

            User user = new User();
            user.setId(newEmployee.getId());
            user.setUsername(employeeResquest.getUserName());
            user.setPassword(employeeResquest.getPassword());

            userService.saveUser(user);

            employeeResquest = null;
            employeeRepository.save(newEmployee);

            return true;
    }



}
