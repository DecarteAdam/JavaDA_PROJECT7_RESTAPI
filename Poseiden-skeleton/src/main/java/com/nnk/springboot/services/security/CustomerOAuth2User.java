package com.nnk.springboot.services.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

public class CustomerOAuth2User implements OAuth2User {
    private final OAuth2User oAuth2User;

    public CustomerOAuth2User(OAuth2User oAuth2User) {
        this.oAuth2User = oAuth2User;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return oAuth2User.getAuthorities();
    }

    @Override
    public Map<String, Object> getAttributes() {
        return oAuth2User.getAttributes();
    }

    @Override
    public String getName() {
        return oAuth2User.getAttributes().get("name").toString();
    }

    public String getFullName(){
        return oAuth2User.getAttributes().get("name").toString();
    }

    public String getEmail(){
        return oAuth2User.getAttributes().get("email").toString();
    }
}
