package peaksoft.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import peaksoft.dto.feedbackDto.FeedbackRequest;
import peaksoft.dto.feedbackDto.FeedbackResponse;
import peaksoft.entity.Feedback;
import peaksoft.entity.User;
import peaksoft.repository.FeedbackRepository;
import peaksoft.repository.UserRepository;
import peaksoft.service.FeedbackService;

@Service
@RequiredArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {
    private final FeedbackRepository feedbackRepository;
    private final UserRepository userRepository;

    @Override
    public FeedbackResponse save(Long userId, FeedbackRequest feedbackRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findUserByEmail(email).orElseThrow(
                () -> new RuntimeException("User with email " +
                        email + " not found!!!"));
        Feedback feedback = feedbackRequest.build();
        user.addFeedback(feedback);
        feedbackRepository.save(feedback);
        return new FeedbackResponse(
                feedback.getId(),
                feedback.getDescription(),
                feedback.getImage(),
                user.getId(),
                user.getEmail()
        );
    }
}
