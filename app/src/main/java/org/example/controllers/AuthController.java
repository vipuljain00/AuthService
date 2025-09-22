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
    public ResponseEntity signup(@RequestBody UserInfoDto userInfoDto){
        try {
            Boolean isSignedUp = myUserDetailsService.signUpUser(userInfoDto);
            if(Boolean.FALSE.equals(isSignedUp)){
                return new ResponseEntity<>("Already Exist",  HttpStatus.BAD_REQUEST);
            }
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(userInfoDto.getUsername());
            String jwtToken = jwtService.generateJwtToken(userInfoDto.getUsername());

            return new ResponseEntity<>(JWTResponseDto.builder().accessToken(jwtToken)
                    .refreshToken(refreshToken.getToken()).build(), HttpStatus.OK);

        } catch (InvalidException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


}
