package com.labi.schedulerjava.adapters.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.labi.schedulerjava.core.domain.exception.BusinessRuleException;
import com.labi.schedulerjava.core.domain.model.User;
import com.labi.schedulerjava.core.domain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    @Autowired
    private UserService userService;

    public String generateToken(User user) {
        try {
            String[] ministries = user.getUserMinistries().stream()
                    .map(userMinistry -> userMinistry.getMinistry().getName())
                    .toArray(String[]::new);

            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("labi")
                    .withSubject(user.getEmail())
                    .withArrayClaim("ministries", ministries)
                    .withExpiresAt(new Date(System.currentTimeMillis() + expiration))
                    .sign(algorithm);
        }
        catch (JWTCreationException e) {
            throw new BusinessRuleException("Não foi possível gerar o token");
        }
    }

    public boolean validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWT.require(algorithm)
                    .withIssuer("labi")
                    .build()
                    .verify(token)
                    .getSubject();
            return true;
        }
        catch (JWTVerificationException e) {
            return false;
        }
    }

    public String resolveToken(String token) {
        if (token != null && token.startsWith("Bearer "))
            return token.substring(7);
        return null;
    }

    public String getEmailFromToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.require(algorithm)
                .withIssuer("labi")
                .build()
                .verify(token)
                .getSubject();
    }

    public User getUserFromToken(String authHeader) {
        String token = resolveToken(authHeader);

        return userService.findByEmail(getEmailFromToken(token))
                .orElseThrow(() -> new BusinessRuleException("Usuário não encontrado"));
    }
}
