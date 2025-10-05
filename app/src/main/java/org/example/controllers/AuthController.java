package org.example.controllers;

import org.example.Exceptions.InvalidException;
import org.example.entities.RefreshToken;
import org.example.models.UserInfoDto;
import org.example.responses.JWTResponseDto;
import org.example.service.JwtService;
import org.example.service.MyUserDetailsService;
import org.example.service.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    private JwtService jwtService;
    @Autowired
    private RefreshTokenService refreshTokenService;
    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @PostMapping("auth/v1/signup")
    public ResponseEntity<?> signup(@RequestBody UserInfoDto userInfoDto){
        Boolean isSignedUp = myUserDetailsService.signUpUser(userInfoDto);

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userInfoDto.getUsername());
        String jwtToken = jwtService.generateJwtToken(userInfoDto.getUsername());

        return ResponseEntity.ok(JWTResponseDto.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken.getToken())
                .build());
    }


}
