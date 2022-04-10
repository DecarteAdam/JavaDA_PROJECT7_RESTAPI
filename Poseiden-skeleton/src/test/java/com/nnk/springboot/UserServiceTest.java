package com.nnk.springboot;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.services.UserService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    private User user;

    @Before
    public void setUp(){
        user = new User();
        user.setFullname("John Doe");
        user.setUsername("john@test.com");
        user.setRole("USER");
        user.setProvider(User.Provider.LOCAL);
    }

    @After
    public void cleanUp(){
        userRepository.deleteAll();
    }

    @Test
    public void loadUserByUsername(){
        userService.save(user);

        UserDetails userDetails = userService.loadUserByUsername(user.getUsername());

        Assert.assertEquals("john@test.com", userDetails.getUsername());

    }

    @Test
    public void saveUserWithoutPassword(){
        user.setProvider(User.Provider.LOCAL);

        userService.save(user);

        User user1 = userService.getUserByEmail("john@test.com");
        Assert.assertNotNull(user1.getId());
        Assert.assertEquals("John Doe", user1.getFullname());

    }

    @Test
    public void saveUserWithPassword(){
        user.setPassword("john123");

        userService.save(user);

        Optional<User> user1 = Optional.of(userService.findById(user.getId()).get());

        Assert.assertEquals("john@test.com", user1.get().getUsername());

    }

    @Test
    public void updateUser(){
        user.setRole("ADMIN");
        userService.save(user);
        User user2 = userService.getUserByEmail("john@test.com");
        Assert.assertEquals("ADMIN", user2.getRole());
        Assert.assertEquals("John Doe", user2.getFullname());

    }

    @Test
    public void findUser(){
        userService.save(user);

        List<User> userList = userService.findAll();
        Assert.assertTrue(userList.size() > 0);

    }

    @Test
    public void deleteUser() {
        userService.save(user);

        Integer id = user.getId();
        userService.deleteById(id);
        Optional<User> userOptional = userService.findById(id);
        Assert.assertFalse(userOptional.isPresent());

    }
}
