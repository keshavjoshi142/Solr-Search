package com.example.JIO.SolrSearch.Models;


import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Entity
@Table(name = "user")
@Data
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "userId")
    private Long id;

    @Column(name = "username")
    @NotEmpty(message = "*USERNAME IS REQUIRED")
    private String username;

    @Column(name = "password")
    @Length(min = 5 ,message ="*Your password must have at least 5 characters" )
    @NotEmpty(message = "*PASSWORD IS REQUIRED")
    private String password;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "userRole" ,
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    public User() {

    }
}
