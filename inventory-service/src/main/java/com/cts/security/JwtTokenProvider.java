package com.cts.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.List;

@Component
public class JwtTokenProvider {
    @Value("${app.jwt-secret}")
    private String jwtSecret;
    @Value("${app.jwt-expiration-milliseconds}")
    private int jwtExpiry;

//    public String generateToken(Authentication authentication){
//        String username=authentication.getName();
//        Date currentDate=new Date();
//
//        Date expiry=new Date(currentDate.getTime()+jwtExpiry);
//
//        List<String> roles = authentication.getAuthorities()
//                .stream()
//                .map(grantedAuthority -> grantedAuthority.getAuthority())
//                .collect(Collectors.toList());
//
//        String token= Jwts.builder()
//                .subject(username)
//                .issuedAt(new Date())
//                .expiration(expiry)
//                .claim("roles", roles)
//                .signWith(key())
//                .compact();
//
//        return token;
//    }

    private Key key(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public String getUsername(String token) {
        return Jwts.parser()
                .verifyWith((SecretKey) key())
                .build()
                .parseSignedClaims(token).getPayload().getSubject();

    }

    public List<String> getRoles(String token) {
        return Jwts.parser()
                .verifyWith((SecretKey) key())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("roles", List.class);
    }

    public boolean validateToken(String token) {
        Jwts.parser()
                .verifyWith((SecretKey) key())
                .build()
                .parse(token);
        return true;
    }
}