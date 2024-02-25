package dev.aagun.ediindonesia.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(nullable = false)
    @NotNull(message = "userid is required")
    private Integer userid;

    @Column(nullable = false)
    @NotBlank(message = "namalengkap is required")
    private String namalengkap;

    @Column(nullable = false)
    @NotBlank(message = "username is required")
    private String username;

    @Column(nullable = false)
    @NotBlank(message = "password is required")
    private String password;

    private char status;

}
