package com.example.JIO.SolrSearch.Service;

import com.example.JIO.SolrSearch.Modals.Employee;

public interface EmployeeRestService {

    public Employee update(Employee employee);

    public boolean isValid(Employee employee);

    public boolean save(Employee employee);
}
