package com.io.bookstoreapi.controller;

import com.io.bookstoreapi.data.service.UserService;
import com.io.bookstoreapi.domain.User;
import com.sun.mail.iap.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    private UserService userService;

    @RequestMapping("/user")
    public Principal user(Principal user){
        return user;
    }

    @GetMapping("/test")
    public String showTest(
            @RequestParam(name = "myName", defaultValue = "user") String name
    ){
        return String.format("Hello, %s!", name);
    }


    @GetMapping("/getUsers")
    public Iterable<User> getUsers( ){
        return userService.getAllUsers();
    }

    @GetMapping("/getUser")
    public Optional<User> getUser(
            @RequestParam(name = "id") int id
    ){
        return userService.getUser(id);
    }

    @GetMapping("/getUserByUsername")
    public UserDetails getUserByUsername(
            @RequestParam(name = "username") String username
    ){
        return userService.loadUserByUsername(username);
    }

    @PostMapping(
            value = "/signUp",
            consumes = "application/json",
            produces = "application/json"
    )
    public ResponseEntity<?> registerUserAccount(
            @RequestBody User user
    ){
        user.setId(0);
        if(userService.userExists(user)){

            return ResponseEntity
                    .unprocessableEntity()
                    .body("User with that username and/or email already exists");
        }else{
            user = userService.saveUser(user);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(user);
        }
    }

    @PostMapping(
            value = "signIn",
            consumes = "application/json",
            produces = "application/json"
    )
    public User authenticateUser(
            @RequestBody User user
            ) {
        return null;
    }

}
