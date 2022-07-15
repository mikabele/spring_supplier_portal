package com.example.demo.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demo.domain.UserDomain;
import com.example.demo.dto.NonAuthorizedUserDto;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Random;
import java.util.UUID;

@Component
public class JwtTokenUtil {

    private final String secretKey = UUID.randomUUID().toString();
    private final Algorithm ALGORITHM = Algorithm.HMAC256(secretKey);

    public DecodedJWT decodeToken(final String accessToken) {
        System.out.println(accessToken);
        JWTVerifier verifier = JWT.require(ALGORITHM).build();
        return verifier.verify(accessToken);
    }

    public String generateToken(User user) {
        return "Bearer " + JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 864_000_000))
                //.withIssuer(requestURI)
                .sign(ALGORITHM);
    }
}
