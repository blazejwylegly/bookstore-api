package com.io.bookstoreapi.data.repositories;

import com.io.bookstoreapi.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer>, CustomUserRepository {

}
