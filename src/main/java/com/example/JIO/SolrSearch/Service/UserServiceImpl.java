package com.example.JIO.SolrSearch.Service;


import com.example.JIO.SolrSearch.Models.Role;
import com.example.JIO.SolrSearch.Models.User;
import com.example.JIO.SolrSearch.Repository.RoleRepository;
import com.example.JIO.SolrSearch.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;



    @Override
    public void saveUser(User user){

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        Role userRole = roleRepository.findByRole("ADMIN");

        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));

        userRepository.save(user);

    }

    @Override
    public Optional<User> findByUsername(String username)
    {
        return userRepository.findByUsername(username);
    }


}
