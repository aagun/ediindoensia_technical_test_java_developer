package dev.aagun.ediindonesia.security;

import at.favre.lib.crypto.bcrypt.BCrypt;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Configuration;

@Builder
@NoArgsConstructor
@Configuration
public class PasswordManager {

    public String hashPw(String password) {
        return this.hashPw(10, password);
    }

    public String hashPw(int rounds, String password) {
        return BCrypt.withDefaults().hashToString(rounds, password.toCharArray());
    }

    public boolean verify(String password, String hashedPassword) {
        BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), hashedPassword);
        return result.verified;
    }
}
