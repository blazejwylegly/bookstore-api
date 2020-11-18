package com.io.bookstoreapi.data.repositories;

import com.io.bookstoreapi.domain.ERole;
import com.io.bookstoreapi.domain.Role;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import java.util.Optional;

public class CustomRoleRepositoryImpl implements CustomRoleRepository{

    @Autowired
    EntityManager entityManager;

    @Override
    public Optional<Role> findByName(ERole name) {
        return Optional.empty();
    }
}
