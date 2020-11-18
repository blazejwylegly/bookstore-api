package com.io.bookstoreapi.data.service;

import com.io.bookstoreapi.data.repositories.UserRepository;
import com.io.bookstoreapi.domain.User;

import com.io.bookstoreapi.exceptions.UserAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public Optional<User> getUser(int id){
        return userRepository.findById(id);
    }

    @Transactional
    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public User saveUser(User user) {
        if(userExists(user)) throw new UserAlreadyExistsException();

        user.setPassword(
                passwordEncoder.encode(user.getPassword())
        );
        return userRepository.save(user);
    }


    @Override
    @Transactional
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userRepository.findByUsername(s);
    }

    @Transactional
    public boolean userExists(User user){
        return userRepository.existsByEmailAddress(user.getEmailAddress()) ||
                userRepository.existsByUsername(user.getUsername());
    }


}
