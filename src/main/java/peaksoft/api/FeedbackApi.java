package peaksoft.api;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.feedbackDto.FeedbackRequest;
import peaksoft.dto.feedbackDto.FeedbackResponse;
import peaksoft.service.FeedbackService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/feedbacks")
public class FeedbackApi {
    private final FeedbackService feedbackService;

    @PostMapping("/{userId}")
    @Secured("USER")
    public ResponseEntity<FeedbackResponse> saveFeedback(@RequestBody FeedbackRequest feedbackRequest,
                                                         @PathVariable Long userId){
        return ResponseEntity.ok(feedbackService.save(userId,feedbackRequest));
    }
}
