package dev.aagun.ediindonesia.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse<T> {
    private String status;
    private String message;
    private T data = null;
}
