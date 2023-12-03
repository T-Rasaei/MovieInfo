package com.moviedb.movieinfo.controller;

import com.moviedb.movieinfo.domain.AuthorizeRequest;
import com.moviedb.movieinfo.domain.UserRequest;
import com.moviedb.movieinfo.domain.UserResponse;
import com.moviedb.movieinfo.service.UserService;
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
    public final static String BASE_URL="/api/v1/user";
    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<UserResponse> create(
            @RequestBody UserRequest request
    ) {
        return ResponseEntity.ok(userService.createUser(request));
    }
    @PostMapping("/authorize")
    public ResponseEntity<UserResponse> authenticate(
            @RequestBody AuthorizeRequest request
    ) {
        return ResponseEntity.ok(userService.authorize(request));
    }

}
