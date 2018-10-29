package com.example.JIO.SolrSearch.Service;

import com.example.JIO.SolrSearch.Models.Employee;
import com.example.JIO.SolrSearch.Models.EmployeeDTO;

public interface EmployeeRestService {

    public Employee update(Employee employee);


    public boolean save(EmployeeDTO employeeDTO);
}
