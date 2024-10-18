package br.com.santander.userservice.repository;

import br.com.santander.userservice.entities.Role;
import br.com.santander.userservice.entities.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void itShouldCheckIfUserExistsByEmail() {
        // given
        String email = "admin@culysoft.com";
        User user = createNewUser();
        userRepository.save(user);

        // when
        boolean expected = userRepository.existsByEmail(email);

        // then
        assertTrue(expected);
    }

    @Test
    void itShouldCheckIfUserDoesNotExistsByEmail() {
        // given
        String email = "admin@culysoft.com";

        // when
        boolean expected = userRepository.existsByEmail(email);

        // then
        assertFalse(expected);
    }

    @Test
    void itShouldReturnUsersWhenFindByEmail() {
        // given
        User user = createNewUser();
        userRepository.save(user);

        // when
        Optional<User> optionalUser = userRepository.findByEmail(user.getEmail());

        // then
        assertTrue(optionalUser.isPresent());
    }

    @Test
    void itShouldReturnEmptyWhenFindByEmail() {
        // given
        String email = "admin@culysoft.com";

        // when
        Optional<User> optionalUser = userRepository.findByEmail(email);

        // then
        assertTrue(optionalUser.isEmpty());
    }

    private User createNewUser() {
        return User.builder()
                .email("admin@culysoft.com")
                .name("Admin")
                .password("1234")
                .role(Role.ADMIN)
                .status(true)
                .createdAt(LocalDateTime.now())
                .build();
    }
}