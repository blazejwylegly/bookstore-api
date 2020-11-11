package com.io.bookstoreapi.data.repositories;

import com.io.bookstoreapi.domain.User;

public interface CustomUserRepository {
    public User findByEmailAddress(String emailAddress);
    public User findByUsername(String username);

    public boolean existsByEmailAddress(String emailAddress);
    public boolean existsByUsername(String username);
}
