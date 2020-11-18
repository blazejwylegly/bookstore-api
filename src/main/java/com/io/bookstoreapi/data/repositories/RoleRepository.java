package com.io.bookstoreapi.data.repositories;

import com.io.bookstoreapi.domain.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Integer> {
}
