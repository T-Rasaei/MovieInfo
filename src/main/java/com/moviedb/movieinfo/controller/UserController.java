package com.moviedb.movieinfo.controller;

import com.moviedb.movieinfo.domain.UserRequest;
import com.moviedb.movieinfo.domain.UserResponse;
import com.moviedb.movieinfo.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(UserController.BASE_URL)
@RequiredArgsConstructor
public class UserController {
    public static final String BASE_URL="/api/v1/user";
    private final UserService userService;

    @PostMapping("/create")
    @Tag(name = "Create user",
    description = "For first time you should create user and access token")
    public ResponseEntity<UserResponse> create(
            @RequestBody UserRequest request
    ) {
        return ResponseEntity.ok(userService.createUser(request));
    }
    @PostMapping("/authorize")
    @Tag(name = "Access to token",
    description = "If you have already created your user, you can receive the token for your user in this section")
    public ResponseEntity<UserResponse> authenticate(
            @RequestBody UserRequest request
    ) {
        return ResponseEntity.ok(userService.authorize(request));
    }

}
