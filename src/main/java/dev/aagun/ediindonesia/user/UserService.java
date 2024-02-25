package dev.aagun.ediindonesia.user;

import dev.aagun.ediindonesia.security.PasswordManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordManager passwordManager;

    public List<User> getDataUsers() {
        return userRepository.findAll();
    }

    public User getDataUser(Integer userId) {
        return userRepository.findById(userId).orElse(new User());
    }

    public void setDataUser(User request) throws NoSuchAlgorithmException {
        String hashedPassword  = passwordManager.hashPw(request.getPassword());

        User newUser = User.builder()
                .userid(request.getUserid())
                .namalengkap(request.getNamalengkap())
                .username(request.getUsername())
                .password(hashedPassword)
                .status(request.getStatus())
                .build();

        userRepository.save(newUser);
    }
}
