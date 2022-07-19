package com.example.demo.repository;

import com.example.demo.domain.UserDomain;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface AuthRepository extends CrudRepository<UserDomain, UUID> {
  Optional<UserDomain> findByUsername(String username);
}
