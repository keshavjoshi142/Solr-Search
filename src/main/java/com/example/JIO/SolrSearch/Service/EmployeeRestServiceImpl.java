package com.example.JIO.SolrSearch.Service;

import com.example.JIO.SolrSearch.Modals.Employee;
import com.example.JIO.SolrSearch.Repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public class EmployeeRestServiceImpl implements EmployeeRestService{

    @Autowired
    private EmployeeRepository employeeRepository;


    @Override
    public Employee update(Employee employee)
    {
        final Optional<Employee> retrievedEmployee = employeeRepository.findById(employee.getId());


        if(retrievedEmployee.isPresent())
        {
            employeeRepository.delete(retrievedEmployee.get());

            retrievedEmployee.get().setFirstName(employee.getFirstName());
            retrievedEmployee.get().setLastName(employee.getLastName());
            retrievedEmployee.get().setEmailId(employee.getEmailId());
            retrievedEmployee.get().setOfficeCirlce(employee.getOfficeCirlce());
            retrievedEmployee.get().setOfficeCity(employee.getOfficeCity());
            retrievedEmployee.get().setSkills(employee.getSkills());
            retrievedEmployee.get().setSuggestion(null);

            employeeRepository.save(retrievedEmployee.get());

            return retrievedEmployee.get();
        }
        else
        {
            return null;
        }
    }

    @Override
    public boolean isValid(Employee employee)
    {
        if(employee.getFirstName().equals("") || employee.getLastName().equals("") || employee.getSkills().size() == 0)
        {
            return false;
        }
        else
            return true;
    }

    public boolean save(Employee employee)
    {

        if(!isValid(employee))
        {
            return false;
        }
        else
        {
            Employee newEmployee = new Employee.MyBuilder().id(employee.getId()).firstName(employee.getFirstName()).lastName(employee.getLastName()).emailId(employee.getEmailId()).phoneNo(employee.getPhoneNo()).skills(employee.getSkills()).process().mybuild();
            employee = null;
            employeeRepository.save(newEmployee);

            return true;
        }
    }


}
