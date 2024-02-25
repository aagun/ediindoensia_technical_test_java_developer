package dev.aagun.ediindonesia.security;

import at.favre.lib.crypto.bcrypt.BCrypt;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

@Builder
@NoArgsConstructor
@Configuration
public class PasswordManager {

    public String hashPw(String password) throws NoSuchAlgorithmException {
        return this.hashPw(16, password);
    }

    public String hashPw(int rounds, String password) throws NoSuchAlgorithmException {
        return BCrypt.withDefaults().hashToString(rounds, password.toCharArray());
    }

    public boolean verify(String password, String hashedPassword) throws NoSuchAlgorithmException {
        BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), hashedPassword);
        return result.verified;
    }
}
