package com.example.JIO.SolrSearch.Models;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Projects {

    private String projectName;
    private  String From;
    private  String To;
    private String description;
}
