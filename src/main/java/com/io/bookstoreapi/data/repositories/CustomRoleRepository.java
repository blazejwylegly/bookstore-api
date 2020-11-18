package com.io.bookstoreapi.data.repositories;

import com.io.bookstoreapi.domain.ERole;
import com.io.bookstoreapi.domain.Role;

import java.util.Optional;

public interface CustomRoleRepository {
    public Optional<Role> findByName(ERole name);
}
