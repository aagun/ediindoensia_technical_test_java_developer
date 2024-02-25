package dev.aagun.ediindonesia.config;

import dev.aagun.ediindonesia.security.PasswordManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    public PasswordManager passwordManager() {
        return new PasswordManager();
    }
}
