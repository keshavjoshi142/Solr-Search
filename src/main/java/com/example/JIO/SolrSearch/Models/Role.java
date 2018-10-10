package com.example.JIO.SolrSearch.Models;


import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "roleId")
    private Long id;

    @Column(name = "role")
    private String role;
}
