package com.example.JIO.SolrSearch.Config;


import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.repository.config.EnableSolrRepositories;
import org.springframework.context.annotation.ComponentScan;


@Configuration
@EnableSolrRepositories(basePackages = "com.example.JIO.SolrSearch.Repository" ,
                namedQueriesLocation = "classpath:application.properties")
@ComponentScan
public class SolrConfig {

    @Bean
    public SolrClient solrClient()
    {
        return new HttpSolrClient("http://localhost:8985/solr");
    }

    @Bean
    public SolrTemplate solrTemplate(SolrClient client) throws Exception
    {
        return new SolrTemplate(client);
    }
}
