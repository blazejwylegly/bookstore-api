package com.io.bookstoreapi.data.repositories;

import com.io.bookstoreapi.domain.User;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public class CustomUserRepositoryImpl implements CustomUserRepository{

    @Autowired
    private EntityManager entityManager;

    @Override
    public User findByEmailAddress( String emailAddress){

        Query query = entityManager.createQuery("select u from User u " +
                "where u.emailAddress like :mail", User.class)
                .setParameter("mail", emailAddress);
        try{
            return (User) query.getSingleResult();
        }catch (Exception ex){
            return null;
        }
    }

    @Override
    public User findByUsername(String username) {
        Query query = entityManager.createQuery("select u from User u " +
                "where u.username like :username", User.class)
                .setParameter("username", username);
        try{
            return (User) query.getSingleResult();
        }catch (Exception ex){
            return null;
        }
    }

    @Override
    public boolean existsByEmailAddress(String emailAddress) {
        return findByEmailAddress(emailAddress) != null;
    }

    @Override
    public boolean existsByUsername(String username) {
        return findByUsername(username) != null;
    }
}
