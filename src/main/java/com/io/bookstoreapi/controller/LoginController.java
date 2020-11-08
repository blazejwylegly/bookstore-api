package com.io.bookstoreapi.controller;

import com.io.bookstoreapi.data.service.UserService;
import com.io.bookstoreapi.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.http.HttpResponse;
import java.security.Principal;

@RestController
@RequestMapping("/api/users")
public class LoginController {

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


    @PostMapping("/signUp")
    public ResponseEntity<?> registerUserAccount(
            @Valid @RequestBody User user,
            HttpResponse<?> response
    ){
        return null;
//        try {
//            return ResponseEntity.ok(
//
//            )
//        }
//        catch(Exception ex){
//        }
//
////        return null;
//        return user;
    }

    @PostMapping(value = "signIn", consumes = "application/json")
    public ResponseEntity<?> authenticateUser(
            @RequestBody User user
    ) {
        return null;
    }

}
