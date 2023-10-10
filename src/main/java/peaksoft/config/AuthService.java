package peaksoft.config;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import peaksoft.dto.AuthRequest;
import peaksoft.dto.UserResponseWithToken;
import peaksoft.entity.User;
import peaksoft.repository.UserRepository;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JWTservice jwTservice;

    public UserResponseWithToken login(AuthRequest authRequest) {
        User user = userRepository.findUserByEmail(authRequest.email()).orElseThrow(
                () -> new NoSuchElementException(String.format("User by email: %s not found! ", authRequest.email())));
        String passwordRequest = authRequest.password();
        String dbpassword = user.getPassword();

        if(!passwordEncoder.matches(passwordRequest, dbpassword)){
            throw new RuntimeException("Invalid password!!");
        }

        String token = jwTservice.createToken(user);
        return new UserResponseWithToken(
                user.getId(),
                user.getEmail(),
                user.getRole(),
                token
        );
    }
}
