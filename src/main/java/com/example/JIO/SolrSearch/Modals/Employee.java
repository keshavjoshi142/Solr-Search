package com.example.JIO.SolrSearch.Modals;


import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.Indexed;
import org.springframework.data.solr.core.mapping.SolrDocument;

import java.util.List;

@Builder
@Data
@SolrDocument(solrCoreName = "Employee_data")

public class Employee {

    @Id
    @Indexed(name = "emp_id" , type = "string")
    @NonNull
    private String empId;                       //required

    @Indexed(name = "first_name" , type = "string")
    @NonNull
    private String firstName;                   //required

    @Indexed(name = "last_name" , type = "string")
    @NonNull
    private String lastName;                     //required

    @Indexed(name = "email_id" , type = "string")
    @NonNull
    private String emailId;                       //required

    @Indexed(name = "phone_no" , type = "string")
    private long phoneNo;                          //optional

    @Indexed(name = "office_circle" , type = "string")
    private String officeCirlce;                   //optional

    @Indexed(name = "office_city" , type = "string")
    private String officeCity;                    //optional

    @Indexed
    private List<String> skills;                  //required



}
