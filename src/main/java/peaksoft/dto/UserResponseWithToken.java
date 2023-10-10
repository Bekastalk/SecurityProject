package peaksoft.dto;

public record UserResponseWithToken(
        Long id,
        String email,
        Role role,
        String token
        ) {
}
