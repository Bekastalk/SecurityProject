package peaksoft.service;

import peaksoft.dto.feedbackDto.FeedbackRequest;
import peaksoft.dto.feedbackDto.FeedbackResponse;

public interface FeedbackService {
    FeedbackResponse save(Long userId, FeedbackRequest feedbackRequest);
}
