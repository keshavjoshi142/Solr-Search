package com.example.JIO.SolrSearch.Modals;


import lombok.Builder;
import lombok.Data;
import org.springframework.data.solr.core.mapping.SolrDocument;


@Data
@Builder
public class Skills {

    private String skill_name;

    private String skill_level;

}
