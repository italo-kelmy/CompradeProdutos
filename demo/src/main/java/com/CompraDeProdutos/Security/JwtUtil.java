package com.CompraDeProdutos.Security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {

    private final JwtConfig config;

    @Autowired
    public JwtUtil(JwtConfig config) {
        this.config = config;
    }

    public String generatKey(UserDetails user) {
        return Jwts.builder()
                .signWith(config.secretKey())
                .subject(user.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + config.getExpiration()))
                .compact();
    }

    public Claims extrairAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(config.secretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }


    public <T> T extrairClaims(String token, Function<Claims, T> resolver) {
        return resolver.apply(extrairAllClaims(token));
    }

    public boolean validadeToken(String token, UserDetails user) {
        String usuario = extrairClaims(token, Claims::getSubject);
        return usuario.equals(user.getUsername()) && !isTokenValid(token);
    }

    private boolean isTokenValid(String token) {
        return extrairClaims(token, Claims::getExpiration).before(new Date());
    }


}
