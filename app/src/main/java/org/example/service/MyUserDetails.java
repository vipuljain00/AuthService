package org.example.service;

import org.springframework.security.core.userdetails.UserDetails;

import org.example.entities.UserInfo;
import org.example.entities.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Set;
import java.util.HashSet;


//Custom UserDetail class implementing UserDetails interface
//Object of this class will be returned by loadUserByUsername method of UserDetailsService

public class MyUserDetails extends UserInfo implements UserDetails {


    private final String username;
    private final String password;
    private Set<GrantedAuthority> authorities = new HashSet<>();


    public MyUserDetails(UserInfo user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        Set<GrantedAuthority> authorities = new HashSet<>();
        for(UserRole role : user.getRoles()){
            authorities.add(new SimpleGrantedAuthority(role.getName().toUpperCase()));
        };
        this.authorities = authorities;
    }



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}