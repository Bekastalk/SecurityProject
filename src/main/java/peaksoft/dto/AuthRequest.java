package peaksoft.dto;

public record AuthRequest(
        String email,
        String password
) {
}
