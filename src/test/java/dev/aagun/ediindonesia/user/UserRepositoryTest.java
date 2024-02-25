package dev.aagun.ediindonesia.user;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.jpa.JpaSystemException;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertFalse;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest(properties = {"spring.jpa.properties.javax.persistence.validation.mode=none"})
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Transactional
@Slf4j
class UserRepositoryTest {

    private final UserRepository underTest;

    @Test
    @Order(1)
    @DisplayName("It should save new user successfully")
    void testSave_newUser_successfully() {
        // Given user id and a user
        int userId = 1;
        User user = User.builder()
                .userid(userId)
                .namalengkap("Agun")
                .username("agun")
                .password("SuperSecretPassword")
                .status('T')
                .build();

        // When
        underTest.save(user);

        // Then
        Optional<User> optionalUser = underTest.findById(userId);
        assertThat(optionalUser)
                .isPresent()
                .hasValueSatisfying(c -> assertThat(c)
                            .usingRecursiveComparison()
                            .isEqualTo(user));
    }

    @Test
    @Order(2)
    @DisplayName("It should not save new user when namalengkap is null")
    void testSave_newUser_failureWhenNamLengkapIsNull() {
        // Given user id and a user
        User user = User.builder()
                .userid(1)
                .username("agun")
                .password("SuperSecretPassword")
                .status('T')
                .build();

        // When
        // Then
        assertThatThrownBy(() -> underTest.save(user))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null property references a null or transient value : dev.aagun.ediindonesia.user.User.namalengkap");
    }

    @Test
    @Order(3)
    @DisplayName("It should not save new user when username is null")
    void testSave_newUser_failureWhenUsernameIsNull() {
        // Given user id and a user
        User user = User.builder()
                .userid(1)
                .namalengkap("agun")
                .password("SuperSecretPassword")
                .status('T')
                .build();

        // When
        // Then
        assertThatThrownBy(() -> underTest.save(user))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null property references a null or transient value : dev.aagun.ediindonesia.user.User.username");
    }

    @Test
    @Order(4)
    @DisplayName("It should not save new user when password is null")
    void testSave_newUser_failureWhenPasswordIsNull() {
        // Given user id and a user
        User user = User.builder()
                .userid(1)
                .namalengkap("agun")
                .username("agun")
                .status('T')
                .build();

        // When
        // Then
        assertThatThrownBy(() -> underTest.save(user))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null property references a null or transient value : dev.aagun.ediindonesia.user.User.password");
    }

    @Test
    @Order(4)
    @DisplayName("It should not save new user when userid is null")
    void testSave_newUser_failureWhenUserIdIsNull() {
        // Given user id and a user
        User user = User.builder()
                .namalengkap("agun")
                .username("agun")
                .password("SuperSecretPassword")
                .status('T')
                .build();

        // When
        // Then
        assertThatThrownBy(() -> underTest.save(user)).isInstanceOf(JpaSystemException.class);
    }

    @Test
    @Order(6)
    @DisplayName("It should delete user by userid successfully")
    void testDelete_user_successfully() {
        // Given userId and a user
        int userId= 1;
        User user = User.builder()
                .userid(userId)
                .namalengkap("Agun")
                .username("agun")
                .password("SuperSecretPassword")
                .status('T')
                .build();
        underTest.save(user);

        // When
        underTest.deleteById(userId);

        // Then
        boolean isExist = underTest.existsById(userId);
        assertFalse(isExist);
    }

    @Test
    @Order(7)
    @DisplayName("It should find all user successfully")
    void testFindAll_user_successfully() {
        // Given list user
        User user1 = User.builder()
                .userid(1)
                .namalengkap("Agun")
                .username("agun")
                .password("SuperSecretPassword")
                .status('T')
                .build();

        User user2 = User.builder()
                .userid(2)
                .namalengkap("Agun")
                .username("agun")
                .password("SuperSecretPassword")
                .status('T')
                .build();

        List<User> users = List.of(user1, user2);
        underTest.saveAll(users);

        // When
        // Then
        List<User> listUser = underTest.findAll();
        assertThat(listUser.size()).isEqualTo(2);
    }


}