package com.io.bookstoreapi.data.repositories;

import com.io.bookstoreapi.domain.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class CustomUserRepositoryImpl implements CustomUserRepository{

    @Autowired
    private EntityManager entityManager;

    @Override
    public User findByEmailAddress( @Param("email") String emailAddress){

        return null;
    }

    @Override
    public User findByUsername(String username) {
        return null;
    }
}
