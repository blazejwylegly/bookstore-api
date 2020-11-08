package com.io.bookstoreapi.data.repositories;

import com.io.bookstoreapi.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
}
