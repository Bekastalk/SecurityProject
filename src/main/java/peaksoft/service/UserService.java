package peaksoft.service;

import peaksoft.dto.UserRequest;
import peaksoft.dto.UserResponse;

import java.security.Principal;

public interface UserService {
    UserResponse save(UserRequest userRequest);

    UserResponse update(Principal principal, Long userId, UserRequest userRequest);
}
