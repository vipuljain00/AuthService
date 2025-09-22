package org.example.service;

import org.example.Exceptions.InvalidException;
import org.example.Utilities.ValidateUtil;
import org.example.models.UserInfoDto;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;

 import org.springframework.stereotype.Component;
 import lombok.AllArgsConstructor;
 import lombok.Data;

import org.example.entities.UserInfo;
import org.example.repo.UserInfoRepo;
import org.example.models.UserInfoDto;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.HashSet;
import java.util.Objects;
import java.util.UUID;

 @Component
 @AllArgsConstructor
 @Data
public class MyUserDetailsService implements UserDetailsService{

    private final UserInfoRepo userInfoRepo;

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfo userInfo = userInfoRepo.findByUsername(username);
        if(userInfo == null){
            throw new UsernameNotFoundException(" username not found");
        }
        return new MyUserDetails(userInfo);
    }

    public UserInfo checkIfUserAlreadyExists(UserInfoDto userInfoDto) {
        return userInfoRepo.findByUsername(userInfoDto.getUsername());
    }

    public Boolean signUpUser(UserInfoDto userInfoDto) {
        boolean isValidated = ValidateUtil.validateCredentials(userInfoDto.getUsername(), userInfoDto.getEmail());
        if(!isValidated){ throw new InvalidException("Username or Email not Valid");}
        userInfoDto.setPassword(passwordEncoder.encode(userInfoDto.getPassword()));
        if(Objects.nonNull(checkIfUserAlreadyExists(userInfoDto))){
            return false;
        }
        String userId = UUID.randomUUID().toString();
        userInfoRepo.save(new UserInfo(userId, userInfoDto.getUsername(), userInfoDto.getPassword(), new HashSet<>()));
        return true;
    }

}
