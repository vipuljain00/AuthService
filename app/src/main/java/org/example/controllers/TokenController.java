package org.example.controllers;

import org.example.entities.RefreshToken;
import org.example.requests.AuthRequestDTO;
import org.example.requests.RefreshTokenRequestDTO;
import org.example.responses.JWTResponseDto;
import org.example.service.JwtService;
import org.example.service.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private JwtService jwtService;

    @PostMapping("auth/v1/login")
    public ResponseEntity<?> authenticateAndGetToken(@RequestBody AuthRequestDTO authRequestDTO) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequestDTO.getUsername(), authRequestDTO.getPassword())
            );
            System.out.println("Authentication object: " + authentication);

            if (authentication.isAuthenticated()) {
                RefreshToken refreshToken = refreshTokenService.createRefreshToken(authRequestDTO.getUsername());
                String accessToken = jwtService.generateJwtToken(authRequestDTO.getUsername());
                return ResponseEntity.ok(JWTResponseDto.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken.getToken())
                        .build());
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            // It's better to return 500, unless you know it's always an auth failure
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Login failed: " + ex.getMessage());
        }
    }

    @PostMapping("auth/v1/refreshToken")
    public JWTResponseDto refreshToken(@RequestBody RefreshTokenRequestDTO refreshTokenRequestDTO){
        // This will throw TokenNotFoundException if not found, handled by global exception handler
        RefreshToken refreshToken =  refreshTokenService.findByToken(refreshTokenRequestDTO.getToken());
        RefreshToken verifiedRefreshtoken = refreshTokenService.verifyExpiration(refreshToken);
        String accessToken = jwtService.generateJwtToken(verifiedRefreshtoken.getUserInfo().getUsername());

        return JWTResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshTokenRequestDTO.getToken())
                .build();
    }

}
