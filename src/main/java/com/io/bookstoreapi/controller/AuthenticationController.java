package com.io.bookstoreapi.controller;

import com.io.bookstoreapi.config.security.JWTUtils;
import com.io.bookstoreapi.data.service.UserService;
import com.io.bookstoreapi.domain.User;
import com.io.bookstoreapi.payload.JwtAuthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JWTUtils jwtUtils;

    @PostMapping(
            value = "/signIn"
    )
    public ResponseEntity<?> authenticateUser(
            @RequestBody User user
    ) {

        System.out.println("User from request: " + user.getUsername() + ", " + user.getPassword());
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                    user.getUsername(),
                    user.getPassword()
            )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJWTToken(authentication);

        User userDetails = (User) authentication.getPrincipal();

        return ResponseEntity.ok(new JwtAuthResponse(
                jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmailAddress()
        ));
    }


    @PostMapping(
            value = "/signUp",
            consumes = "application/json",
            produces = "application/json"
    )
    public ResponseEntity<?> registerUserAccount(
            @Valid @RequestBody User user
    ){
        user.setId(0);

        user = userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.OK)
                .body(user);
    }



    @RequestMapping("/user")
    public Principal user(Principal user){
        return user;
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

}
