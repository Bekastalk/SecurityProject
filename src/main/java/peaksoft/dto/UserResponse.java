package peaksoft.dto;

import peaksoft.entity.User;

public record UserResponse(
        Long id,
        String fullName,
        String email,
        Role role
) {
    public static UserResponse  build(User user){
       return new UserResponse(
        user.getId(),
        user.getFullName(),
        user.getEmail(),
        user.getRole());
    }
}
