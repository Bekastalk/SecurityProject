package peaksoft.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import peaksoft.dto.UserRequest;
import peaksoft.dto.UserResponse;
import peaksoft.entity.User;
import peaksoft.repository.UserRepository;
import peaksoft.service.UserService;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private  final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponse save(UserRequest userRequest) {
        User user = userRequest.build();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return UserResponse.build(user);
    }

    @Override
    @Transactional
    public UserResponse update(Principal principal, Long userId, UserRequest userRequest) {
        User userForResponse=new User();
        String email = principal.getName();
        User authUser = userRepository.findUserByEmail(email).orElseThrow(
                () -> new RuntimeException("User with email: " + email + " not found!"));
        if(authUser.getRole().equals(Role.ADMIN)){
            userForResponse=methodUpdate(userRequest,userId);
        } else if (authUser.getRole().equals(Role.USER)) {
            if(authUser.getId().equals(userId)){
                userForResponse=methodUpdate(userRequest, userId);
            }else {
                throw new RuntimeException("Sen bashka userdin dannyilaryn ozgortup jatasyn ");
            }
        }
        return new UserResponse(
                userForResponse.getId(),
                userForResponse.getFullName(),
                userForResponse.getEmail(),
                userForResponse.getRole()
        );
    }

    private User methodUpdate(UserRequest userRequest, Long userId){
        User newUser = userRequest.build();
        User parUser = userRepository.findById(userId).orElseThrow(
                () -> new RuntimeException("User by id: " + userId + " not found!"));
        parUser.setFullName(newUser.getFullName());
        parUser.setEmail(newUser.getEmail());
        return newUser;
    }
}
