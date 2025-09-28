package org.example.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.example.Exceptions.TokenNotFoundException;
import org.example.entities.RefreshToken;
import org.example.entities.UserInfo;

import org.example.repo.RefreshTokenRepo;
import org.example.repo.UserInfoRepo;

import org.springframework.beans.factory.annotation.Autowired;

import java.beans.Transient;
import java.time.Instant;
import java.util.UUID;
import java.util.Optional;

//Service class for handling refresh token operations like creating and verifying refresh token
@Service
public class RefreshTokenService {

    //Dependecy to fetch UserInfo from database
    @Autowired UserInfoRepo userInfoRepo;
    //Dependency to fetch-save RefreshToken from-to database
    @Autowired RefreshTokenRepo refreshTokenRepo;

    
    //Method to create a new refresh token for a user
    public RefreshToken createRefreshToken(String username){

        UserInfo userInfo = userInfoRepo.findByUsername(username);
        if(userInfo == null){
            throw new RuntimeException("User not found");
        }

        // remove Refresh Token if already stored for User
        Optional<RefreshToken> refreshTokenOptional = refreshTokenRepo.findByUserInfo(userInfo);
        refreshTokenOptional.ifPresent(refreshToken -> {
            refreshTokenRepo.delete(refreshToken);
        });

        RefreshToken refreshToken = RefreshToken.builder()
                .userInfo(userInfo)
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(1000*60*60))
                .build();

        return refreshTokenRepo.save(refreshToken);
    }


    //Method to find a RefreshToken entity by its token string
    public RefreshToken findByToken(String token) {
        return refreshTokenRepo.findByToken(token)
            .orElseThrow(() -> new TokenNotFoundException("Refresh Token is not in DB..!!"));
    }

    //Method to verify if the refresh token is valid or not
    public RefreshToken verifyExpiration(RefreshToken refreshToken){
        if(refreshToken.getExpiryDate().compareTo(Instant.now()) < 0){
            refreshTokenRepo.delete(refreshToken);
            throw new RuntimeException(refreshToken.getToken() + "Refresh token expired, Login Again");
        }
        return refreshToken;
    }
}