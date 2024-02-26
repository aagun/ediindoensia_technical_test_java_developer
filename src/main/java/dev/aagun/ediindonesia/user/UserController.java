package dev.aagun.ediindonesia.user;

import dev.aagun.ediindonesia.constant.HttpStatusCommon;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping(
            consumes = {
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_JSON_VALUE
            },
            produces = {
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_JSON_VALUE
            })
    public ResponseEntity<UserResponse<Object>> setDataUser(@RequestBody User user)
            throws NoSuchAlgorithmException {

        userService.setDataUser(user);
        UserResponse<Object> response = UserResponse.builder()
                .status(HttpStatusCommon.SUCCESS.getName())
                .message(HttpStatus.Series.INFORMATIONAL.name().toLowerCase())
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping(
            path = "/{userId}",
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE
            }
    )
    public ResponseEntity<UserResponse<List<User>>> getDataUser(@PathVariable Integer userId) {
        User user = userService.getDataUser(userId);
        UserResponse<List<User>> response = UserResponse.<List<User>>builder()
                .status(HttpStatusCommon.SUCCESS.getName())
                .message(HttpStatus.Series.INFORMATIONAL.name().toLowerCase())
                .data(List.of(user))
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping(
            path = "/all",
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE
            }
    )
    public ResponseEntity<UserResponse<List<User>>> getDataUsers() {
        List<User> users = userService.getDataUsers();
        UserResponse<List<User>> response = UserResponse.<List<User>>builder()
                .status(HttpStatusCommon.SUCCESS.getName())
                .message(HttpStatus.Series.INFORMATIONAL.name().toLowerCase())
                .data(users)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping(path = "/{userId}")
    public ResponseEntity<UserResponse<Object>> delDataUser(@PathVariable Integer userId) {
        userService.delDataUser(userId);
        UserResponse<Object> response = UserResponse.builder()
                .status(HttpStatusCommon.SUCCESS.getName())
                .message(HttpStatus.Series.INFORMATIONAL.name().toLowerCase())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
