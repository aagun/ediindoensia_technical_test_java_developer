package dev.aagun.ediindonesia.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
@RequiredArgsConstructor
class PasswordManagerTest {

    private PasswordManager underTest;

    @BeforeEach
    void beforeEach() {
        underTest = new PasswordManager();
    }

    @Test
    void testHashPw_hashedPassword_successfully() throws NoSuchAlgorithmException {
        // Given password
        String myPassword = "sUpErS3c12eT!";

        // When
        String hashedPassword = underTest.hashPw(myPassword);

        // Then
        assertThat(myPassword).isNotEqualTo(hashedPassword);
        assertFalse(hashedPassword.isEmpty());
        assertTrue(underTest.verify(myPassword, hashedPassword));
    }

    @Test
    void testHashPw_hashedPasswordWithRound_successfully() throws NoSuchAlgorithmException {
        // Given rounds and password
        int rounds = 12;
        String myPassword = "V3r1sUpErS3c12eT!";

        // When
        String hashedPassword = underTest.hashPw(rounds, myPassword);

        // Then
        assertThat(myPassword).isNotEqualTo(hashedPassword);
        assertFalse(hashedPassword.isEmpty());
        assertTrue(underTest.verify(myPassword, hashedPassword));
    }


}