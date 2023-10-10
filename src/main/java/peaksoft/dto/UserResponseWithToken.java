package peaksoft.dto;

import peaksoft.Role;

public record UserResponseWithToken(
        Long id,
        String email,
        Role role,
        String token
        ) {
}
