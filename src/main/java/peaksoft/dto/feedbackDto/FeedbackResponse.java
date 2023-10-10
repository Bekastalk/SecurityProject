package peaksoft.dto.feedbackDto;

public record FeedbackResponse(
        Long feedbackId,
        String description,
        String image,
        Long UserId,
        String userEmail
) {
}
