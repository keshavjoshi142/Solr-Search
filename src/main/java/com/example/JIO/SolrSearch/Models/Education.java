package com.example.JIO.SolrSearch.Models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Education {

    private String instName;
    private String degree;
    private String from;
    private String To;

}
