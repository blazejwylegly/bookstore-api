package com.io.bookstoreapi.data.repositories;

import com.io.bookstoreapi.domain.User;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;

public class CustomUserRepositoryImpl implements CustomUserRepository{

    @Autowired
    private EntityManager entityManager;

    @Override
    public User findByEmailAddress( String emailAddress){

        return entityManager.createQuery("select u from User u " +
                "where u.emailAddress like :mail", User.class)
                .setParameter("mail", emailAddress)
                .getSingleResult();
    }

    @Override
    public User findByUsername(String username) {
        return entityManager.createQuery("select u from User u " +
                "where u.emailAddress like :username", User.class)
                .setParameter("username", username)
                .getSingleResult();
    }

    @Override
    public boolean existsByEmailAddress(String emailAddress) {
        return findByEmailAddress(emailAddress) == null;
    }

    @Override
    public boolean existsByUsername(String username) {
        return findByUsername(username) == null;
    }
}
