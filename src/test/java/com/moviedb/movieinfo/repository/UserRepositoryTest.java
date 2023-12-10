package com.moviedb.movieinfo.repository;

import com.moviedb.movieinfo.config.AppConfig;
import com.moviedb.movieinfo.domain.Role;
import com.moviedb.movieinfo.domain.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    private User user;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .userName("test")
                .password(new BCryptPasswordEncoder().encode("test"))
                .role(Role.USER)
                .build();
        userRepository.save(user);
    }

    @AfterEach
    void afterEach(){
        userRepository.delete(user);
    }

    @Test
    void checkFindByUserName() {
        Optional<User> result = userRepository.findByUserName("test");
        assertThat(result.isEmpty());
    }
}