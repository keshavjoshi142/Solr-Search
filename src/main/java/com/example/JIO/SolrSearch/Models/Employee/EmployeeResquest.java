package com.example.JIO.SolrSearch.Models.Employee;


import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class EmployeeResquest {

    private String name;
    private String userName;
    private String emailId;
    private String phoneNo;
    private String password;
    private List<String> skills;
    private List<Education> education;
    private List<Projects> projects;
    private String image;


}
