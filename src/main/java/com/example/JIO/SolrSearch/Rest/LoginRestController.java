package com.example.JIO.SolrSearch.Rest;


import com.example.JIO.SolrSearch.Models.User;
import com.example.JIO.SolrSearch.Repository.EmployeeRepository;
import com.example.JIO.SolrSearch.Repository.UserRepository;
import com.example.JIO.SolrSearch.Service.JwtAuthenticationService.JwtAuthenticationResponse;
import com.example.JIO.SolrSearch.Service.JwtAuthenticationService.JwtTokenProvider;
import com.example.JIO.SolrSearch.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController

public class LoginRestController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private EmployeeRepository employeeRepository;

    private User user;

    private static final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);



   @RequestMapping(value = "/login/data" ,consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
   public ResponseEntity<?> authicateUser(@RequestBody User user)
   {
       if(Objects.isNull(user)) {

            logger.log(Level.WARNING , "Empty Credentials");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

       }

       Optional<User> new_user = userService.findByUsername(user.getUsername());

       if(new_user.isPresent()) {

           if(bCryptPasswordEncoder.matches(user.getPassword() , new_user.get().getPassword())) {

               String jwt = jwtTokenProvider.generateToken(new_user.get().getId());
               logger.log(Level.INFO , "Successfully LoggedIn");
               return ResponseEntity.ok(new JwtAuthenticationResponse(jwt , user.getUsername()));

           } else {

               logger.log(Level.WARNING , "Incorrect Password");
               return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

           }
       } else {
           logger.log(Level.WARNING , "Incorrect Username");
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
       }

   }

    @GetMapping("/check/{userName}")
    public ResponseEntity<?> checkUserName(@PathVariable String userName) {

       if(Objects.isNull(userName)) {

           logger.log(Level.WARNING , "Empty Username");
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

       }

       Optional<User> user = userService.findByUsername(userName);

       if(user.isPresent()) {

           logger.log(Level.INFO, "Username is already taken");
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
       }else {
           return ResponseEntity.status(HttpStatus.CREATED).build();
       }
   }

}
