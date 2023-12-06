package com.moviedb.movieinfo.service;

import com.moviedb.movieinfo.domain.*;
import com.moviedb.movieinfo.jwt.JwtAuthenticationFilter;
import com.moviedb.movieinfo.jwt.JwtService;
import com.moviedb.movieinfo.repository.TokenRepository;
import com.moviedb.movieinfo.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @InjectMocks
    private UserService userService;
    @Mock
    private TokenRepository tokenRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private JwtService jwtService;
    @Mock
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    @Mock
    private UserRepository userRepository;
    private User user;
    private Token token;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .userName("test")
                .password(new BCryptPasswordEncoder().encode("test"))
                .role(Role.USER)
                .build();

        token = Token.builder()
                .token("token")
                .tokenType(TokenType.BEARER)
                .user(user)
                .build();
    }

    @Test
    void checkCreateUser() {
        when(userRepository.save(any())).thenReturn(user);
        UserResponse response = userService.createUser(UserRequest.builder()
                .userName("test")
                .password("password")
                .build());
        assertThat(response.getUserName()).isEqualTo(user.getUsername());

    }

    @Test
    void checkAuthorize() {
        when(userRepository.findByUserName(any())).thenReturn(Optional.of(user));
        when(tokenRepository.save(any())).thenReturn(token);
        when(jwtService.generateToken(any())).thenReturn(token.getToken());
        UserResponse response = userService.authorize(AuthorizeRequest.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .build());
        assertThat(response.getUserName()).isEqualTo(user.getUsername());
        assertThat(response.getAccessToken()).isEqualTo(token.token);

    }
}