package com.example.JIO.SolrSearch.Service;

import com.example.JIO.SolrSearch.Models.Education;
import com.example.JIO.SolrSearch.Models.Employee;
import com.example.JIO.SolrSearch.Models.EmployeeDTO;
import com.example.JIO.SolrSearch.Models.Projects;
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


    public boolean save(EmployeeDTO employeeDTO) {

            List<String> instName = new ArrayList<String>();
            List<String> degree = new ArrayList<String>();
            List<String> eduFrom = new ArrayList<String>();
            List<String> eduTo = new ArrayList<String>();

            List<String> projectName = new ArrayList<String>();
            List<String> projFrom = new ArrayList<String>();
            List<String> projTo = new ArrayList<String>();
            List<String> description = new ArrayList<String>();

            List<Education> educationList = employeeDTO.getEducation();
            List<Projects> projectsList = employeeDTO.getProjects();

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

            Employee newEmployee = new Employee.MyBuilder().id(randomLongGenerator.getRandomLong()).name(employeeDTO.getName()).userName(employeeDTO.getUserName()).emailId(employeeDTO.getEmailId()).
                                        phoneNo(employeeDTO.getPhoneNo()).skills(employeeDTO.getSkills()).instName(instName).degree(degree).eduFrom(eduFrom).eduTo(eduTo).
                                        projectName(projectName).projFrom(projFrom).projTo(projTo).description(description).image(employeeDTO.getImage()).weight(Long.valueOf(0)).mybuild();

            User user = User.builder().id(newEmployee.getId()).username(employeeDTO.getUserName()).password(employeeDTO.getPassword()).build();

            userService.saveUser(user);

            employeeDTO = null;
            employeeRepository.save(newEmployee);

            return true;
    }



}
