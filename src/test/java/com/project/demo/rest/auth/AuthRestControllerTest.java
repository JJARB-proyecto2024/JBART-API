package com.project.demo.rest.auth;

import com.project.demo.logic.entity.auth.AuthenticationService;
import com.project.demo.logic.entity.auth.JwtService;
import com.project.demo.logic.entity.user.LoginResponse;
import com.project.demo.logic.entity.user.User;
import com.project.demo.logic.entity.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class AuthRestControllerTest {

    @MockBean
    private AuthenticationService authenticationService;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private AuthRestController authRestController;

    @Test
    void authenticate_UserExists_ReturnsLoginResponse() {
        // given
        User user = new User();
        user.setEmail("user.buyer@gmail.com");
        user.setPassword("newPassword123");

        User authenticatedUser = new User();
        authenticatedUser.setEmail("user.buyer@gmail.com");

        // Mocking the behavior of services and repository
        when(authenticationService.authenticate(user)).thenReturn(authenticatedUser);
        when(jwtService.generateToken(authenticatedUser)).thenReturn("jwtToken");
        when(jwtService.getExpirationTime()).thenReturn(3600L);
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(authenticatedUser));

        // when
        ResponseEntity<LoginResponse> response = authRestController.authenticate(user);

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("jwtToken", response.getBody().getToken());
        assertEquals(3600L, response.getBody().getExpiresIn());
        assertEquals(authenticatedUser, response.getBody().getAuthUser());
    }

    @Test
    void authenticate_UserNotFound_ReturnsLoginResponseWithoutAuthUser() {
        // given
        User user = new User();
        user.setEmail("user.buyer@gmail.com");
        user.setPassword("newPassword123");

        // Mocking the behavior of services and repository
        when(authenticationService.authenticate(user)).thenReturn(null);
        when(jwtService.generateToken(any())).thenReturn("jwtToken");
        when(jwtService.getExpirationTime()).thenReturn(3600L);
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());

        // when
        ResponseEntity<LoginResponse> response = authRestController.authenticate(user);

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("jwtToken", response.getBody().getToken());
        assertEquals(3600L, response.getBody().getExpiresIn());
        assertNull(response.getBody().getAuthUser());
    }
}