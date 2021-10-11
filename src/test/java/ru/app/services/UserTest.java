package ru.app.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.app.ProgramTests;
import ru.app.models.Role;
import ru.app.models.User;
import ru.app.repositories.UserRepository;

import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class UserTest extends ProgramTests {
    @Mock
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Mock
    private UserDetailsServiceImpl userDetailsService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Captor
    private ArgumentCaptor<User> captor;

    @Autowired
    public void setUserAuthService(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Test
    public void UserShouldBeRegistered() {
        String encodedPassword = passwordEncoder.encode("qwerty");
        User newUser = new User();
        newUser.setUsername("test");
        newUser.setPassword(encodedPassword);
        newUser.setId(2L);
        var set = new HashSet<Role>();
        set.add(new Role(2L, "ROLE_USER"));
        newUser.setRoles(set);

        when(userRepository.save(any(User.class))).thenReturn(newUser);
        User user = userRepository.save(newUser);

        assertThat(user).isNotNull();
        verify(userRepository).save(captor.capture());
        User captured = captor.getValue();
        Assertions.assertEquals(newUser.getUsername(), captured.getUsername());
    }

    @Test
    public void UserShouldBeFound() {
        var set = new HashSet<Role>();
        set.add(new Role(2L, "ROLE_USER"));
        User user = new User();
        user.setUsername("user");
        user.setPassword("password");
        user.setId(1L);
        user.setRoles(set);
        userService.save(user);

        String username = "user";

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        Assertions.assertEquals(username, userDetails.getUsername());
    }
}
