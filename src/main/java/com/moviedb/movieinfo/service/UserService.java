package com.moviedb.movieinfo.service;

import com.moviedb.movieinfo.domain.*;
import com.moviedb.movieinfo.jwt.JwtService;
import com.moviedb.movieinfo.repository.TokenRepository;
import com.moviedb.movieinfo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final TokenRepository tokenRepository;
    private final AuthenticationManager authenticationManager;
    public UserResponse createUser(UserRequest request){
        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();
        var savedUser = userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        saveUserToken(savedUser,jwtToken);
        return UserResponse
                .builder()
                .email(user.getUsername())
                .accessToken(jwtToken)
                .build();
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .build();
        tokenRepository.save(token);
    }

    public UserResponse authorize(AuthorizeRequest request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(), request.getPassword()));
        var user = userRepository.findByEmail(request.getUsername())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        saveUserToken(user,jwtToken);
        return UserResponse
                .builder()
                .email(user.getUsername())
                .accessToken(jwtToken)
                .build();
    }

}

