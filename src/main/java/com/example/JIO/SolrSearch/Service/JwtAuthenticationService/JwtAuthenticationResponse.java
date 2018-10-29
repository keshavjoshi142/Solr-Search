package com.example.JIO.SolrSearch.Service.JwtAuthenticationService;


import lombok.Data;

@Data
public class JwtAuthenticationResponse {

    private String accessToken;

    private String userName;

    private String tokenType = "Bearer";

    public JwtAuthenticationResponse(String accessToken , String userName){

        this.accessToken = accessToken;
        this.userName = userName;
    }
}
