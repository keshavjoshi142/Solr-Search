package com.example.JIO.SolrSearch.Service;

import com.example.JIO.SolrSearch.Models.User;

import java.util.Optional;

public interface UserService {

    void saveUser(User user);

    Optional<User> findByUsername(String username);


}
