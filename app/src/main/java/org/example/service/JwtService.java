package org.example.service;

//This class will handle JWT token creation and validation

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.function.Function;

import java.security.Key;
import io.jsonwebtoken.security.Keys;


import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Claims;

@Service
public class JwtService {
    
    private static final String SECRET = "357638792F423F4428472B4B6250655368566D597133743677397A2443264629";
    
    //Method to generate JWT Token
    public String generateJwtToken(String username){
        Map<String, Object> claims = new HashMap<>();
        return createJwtToken(username, claims);
    }

    //Method to extract username from JWT token
    public String extractUsername (String token){
        return extractClaim(token, Claims::getSubject);
    }

    //Method to extract expiry date from JWT token
    public Date extractExpiryDate (String token){
        return extractClaim(token, Claims::getExpiration);
    }

    //Method to check if JWT token is expired
    public boolean isTokenExpired(String token){
        return extractExpiryDate(token).before(new Date());
    }

    //Method to validate JWT token
    public boolean validateToken(String token, UserDetails userDetails){
        String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));        
    }


    //Utilities....

    //Method to create JWT token (used by generateToken method)
    private String createJwtToken(String username, Map<String, Object> claims){
        return Jwts.builder()
            .setClaims(claims)
            .setSubject(username)
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + 1000 *60 *30))
            .signWith(getSignKey(), SignatureAlgorithm.HS256)
            .compact();
    };

    //Method to extract a single specified claim from All Claims in JWT token
    private  <T> T extractClaim(String token, Function<Claims, T> claimResolver){
        Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    //Method to extract username from JWT token
    private Claims extractAllClaims(String token){
        return Jwts
            .parserBuilder()
            .setSigningKey(getSignKey())
            .build()
            .parseClaimsJws(token)
            .getBody();
    };

    //Method to generate Signing Key
    private Key getSignKey(){
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
