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
import org.springframework.web.servlet.DispatcherServlet;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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


/*    @PostConstruct
*//*   public void loadData() {
       user = User.builder().id(new Long(1)).username("1").password("1").build();

       List<User> userList = new ArrayList<>();

       userList.add(user);

       userService.saveUser(user);

   }*/


   @RequestMapping(value = "/login/data" ,consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
   public ResponseEntity<?> authicateUser(@RequestBody User user)
   {
       Optional<User> new_user = userService.findByUsername(user.getUsername());

       if(new_user.isPresent() && bCryptPasswordEncoder.matches(user.getPassword() , new_user.get().getPassword()))
       {
           System.out.println("kkkkkkkkkkkkkkkkkkkkkkkkkk");
           String jwt = jwtTokenProvider.generateToken(new_user.get().getId());
           return ResponseEntity.ok(new JwtAuthenticationResponse(jwt , user.getUsername()));
       }
       else
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
   }

    @GetMapping("/check/{userName}")
    public ResponseEntity<?> checkUserName(@PathVariable String userName) {

       Optional<User> user = userService.findByUsername(userName);

       if(user.isPresent()) {
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
       }else {
           return ResponseEntity.status(HttpStatus.CREATED).build();
       }
   }

    @RequestMapping("/registration")
    public User registerEmp()
    {
        User user = User.builder().id(new Long(2)).username("keshav").password(bCryptPasswordEncoder.encode("azsxdc")).build();

        userRepository.save(user);

        return user;
    }



}
