package com.example.JIO.SolrSearch.Repository;

import com.example.JIO.SolrSearch.Models.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.repository.Boost;
import org.springframework.data.solr.repository.SolrCrudRepository;


public interface EmployeeRepository extends SolrCrudRepository<Employee , String> {

    Page<Employee> findBySuggestion(@Boost() String name , Pageable pageable);

    Page<Employee> findBySkillsStartingWith(@Boost() String skills , Pageable pageable);

    Page<Employee> findBySkillsEndingWith(@Boost() String skills , Pageable pageable);

    Page<Employee> findByFlattenedSkills(@Boost() String skills , Pageable pageable);


}
