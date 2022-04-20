package com.nnk.springboot.services.security;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        CustomerOAuth2User oAuth2User = (CustomerOAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getEmail();
        String name = oAuth2User.getFullName();
        User user = userService.getUserByEmail(email);


        if (user == null){
            User newUser = new User();
            newUser.setUsername(email);
            newUser.setFullname(name);
            newUser.setProvider(User.Provider.GOOGLE);
            newUser.setRole("USER");
            //save new user
            userService.save(newUser);
        }
        System.out.println("Customer's email: " + email);
        super.onAuthenticationSuccess(request, response, authentication);

    }
}
