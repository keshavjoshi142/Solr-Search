package com.example.JIO.SolrSearch.Repository;

import com.example.JIO.SolrSearch.Models.Employee.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.solr.repository.Boost;
import org.springframework.data.solr.repository.SolrCrudRepository;
import java.util.List;
import java.util.Optional;


public interface EmployeeRepository extends SolrCrudRepository<Employee , String> {

    List<Employee> findBySuggestion(@Boost() String name  , Sort sort);

    Optional<Employee> findByUserName(String userName);
    
    Optional<Employee> findById(Long id);

    Page<Employee> findByFlattenedSkills(@Boost() String skills , Pageable pageable);


}
