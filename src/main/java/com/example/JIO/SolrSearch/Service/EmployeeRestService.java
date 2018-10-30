package com.example.JIO.SolrSearch.Service;

import com.example.JIO.SolrSearch.Models.Employee.Employee;
import com.example.JIO.SolrSearch.Models.Employee.EmployeeResquest;

public interface EmployeeRestService {

    public Employee update(Employee employee);


    public boolean save(EmployeeResquest employeeResquest);
}
