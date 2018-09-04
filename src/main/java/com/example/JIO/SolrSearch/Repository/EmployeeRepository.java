package com.example.JIO.SolrSearch.Repository;

import com.example.JIO.SolrSearch.Modals.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.solr.repository.Boost;
import org.springframework.data.solr.repository.SolrCrudRepository;
import org.springframework.data.domain.Pageable;


public interface EmployeeRepository extends SolrCrudRepository<Employee , String> {

    Page<Employee> findByFirstName(@Boost() String name , Pageable pageable);

}
