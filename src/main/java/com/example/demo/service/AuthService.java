package com.example.demo.service;

import com.example.demo.repository.AuthRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class AuthService {

  private final AuthRepository authRepository;

  public AuthService(AuthRepository authRepository) {
    this.authRepository = authRepository;
  }

  public Optional<UserDetails> findByUsername(String username) {
    var res = authRepository.findByUsername(username);
    return res.map(
        ud -> {
          var authorities =
              Collections.singleton(new SimpleGrantedAuthority(ud.getRole().toString()));
          return new User(ud.getUsername(), ud.getPassword(), authorities);
        });
  }
}
