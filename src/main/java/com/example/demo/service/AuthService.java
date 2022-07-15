package com.example.demo.service;

import com.example.demo.domain.UserDomain;
import com.example.demo.repository.AuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private AuthRepository authRepository;

    public Optional<UserDomain> verifyLogin(User user) {
        return authRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
    }

    public Optional<UserDetails> findByUsername(String username) {
        var res = authRepository.findByUsername(username);
        return  res.map(ud -> {
            var authorities = Collections.singleton(new SimpleGrantedAuthority(ud.getRole().toString()));
            return new User(ud.getUsername(),ud.getPassword(),authorities);
        });
    }
}
