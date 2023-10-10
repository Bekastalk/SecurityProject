package peaksoft.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.ZonedDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "feedbacks")
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "feedback_gen")
    @SequenceGenerator(name = "feedback_gen",
    sequenceName = "feedback_seq",
    allocationSize = 1)
    private Long id;
    private String description;
    private String image;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
    @ManyToOne
    private User user;

    @PrePersist
    public void preSave(){
        this.createdAt= ZonedDateTime.now();
        this.updatedAt= ZonedDateTime.now();
    }

    @PreUpdate
    public void preUpdate(){
        this.updatedAt=ZonedDateTime.now();
    }

}