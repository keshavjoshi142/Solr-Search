package com.example.JIO.SolrSearch.Config;

import org.apache.solr.client.solrj.impl.XMLResponseParser;

public class QESXMLResponseParser extends XMLResponseParser {
    public QESXMLResponseParser() {
        super();
    }

    @Override
    public String getContentType() {
        return "text/xml; charset=UTF-8";
    }
}
