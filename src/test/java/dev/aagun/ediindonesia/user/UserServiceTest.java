package dev.aagun.ediindonesia.user;

import dev.aagun.ediindonesia.security.PasswordManager;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@RequiredArgsConstructor
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordManager passwordManager;

    @InjectMocks
    private UserService underTest;

    @Test
    @DisplayName("It should return all data users")
    void testGetDataUsers_returnListUser_successfully() {
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

        given(userRepository.findAll()).willReturn(users);

        // When
        List<User> userList = underTest.getDataUsers();

        // Then
        assertThat(userList.size()).isEqualTo(2);
        verify(userRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("It should return a user data by userid")
    void testGetDataUser_returnDataByUserId_successfully() {
        // Given user id and a user
        int userId = 1;
        User user = User.builder()
                .userid(userId)
                .namalengkap("Agun")
                .username("agun")
                .password("SuperSecretPassword")
                .status('T')
                .build();

        given(userRepository.findById(userId)).willReturn(Optional.ofNullable(user));

        // When
        User result = underTest.getDataUser(userId);

        // Then
        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(user);

        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    @DisplayName("It should save new user successfully")
    void testSetDataUser_saveNewUser_successfully() throws NoSuchAlgorithmException {
        // Given request
        User request = User.builder()
                .userid(1)
                .namalengkap("Agun")
                .username("agun")
                .password("hashedPassword")
                .status('T')
                .build();

        // ... hashed password
        given(passwordManager.hashPw(request.getPassword())).willReturn("hashedPassword");

        // When
        underTest.setDataUser(request);

        // Then
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        then(userRepository).should().save(userArgumentCaptor.capture());
        User userArgumentCaptorValue = userArgumentCaptor.getValue();

        assertThat(userArgumentCaptorValue).usingRecursiveComparison().isEqualTo(userArgumentCaptorValue);

        verify(passwordManager, times(1)).hashPw(request.getPassword());
        verify(userRepository, times(1)).save(request);
    }
}