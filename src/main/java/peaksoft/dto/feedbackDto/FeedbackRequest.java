package peaksoft.dto.feedbackDto;

import peaksoft.entity.Feedback;

public record FeedbackRequest(String description, String image) {
    public Feedback build(){
        Feedback feedback=new Feedback();
        feedback.setDescription(this.description);
        feedback.setImage(this.image);
        return feedback;
    }
}
