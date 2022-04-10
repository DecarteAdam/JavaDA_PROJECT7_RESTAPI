package com.nnk.springboot.services;

import com.nnk.springboot.domain.CustomUserDetails;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.List;
import java.util.Optional;

@Service
@SessionAttributes("user")
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        return new CustomUserDetails(user);
    }


    public User getUserByEmail(String email){
        return userRepository.findByUsername(email);
    }


    public void save(User user) {
        if (user.getPassword() != null){
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            user.setPassword(encoder.encode(user.getPassword()));
        }

        userRepository.save(user);
    }

    /**
     * Find bidList by its id
     * @param id of bidList
     * @return bidList
     */
    public Optional<User> findById(int id){
        return this.userRepository.findById(id);
    }

    /**
     * Find all bidLists
     * @return all bidLists
     */
    public List<User> findAll(){
        return this.userRepository.findAll();
    }

    /**
     * Delete bidList by its id
     * @param id of bidList to delete
     */
    public void deleteById(int id){
        this.userRepository.deleteById(id);
    }
}
