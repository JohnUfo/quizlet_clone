package online.muydinov.quizletclone.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class SetAccessRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "requester_id", nullable = false)
    private User requester;

    @ManyToOne
    @JoinColumn(name = "set_id", nullable = false)
    private FlashcardSet flashcardSet;

    @Enumerated(EnumType.STRING)
    private RequestStatus status;

    public enum RequestStatus {
        PENDING, APPROVED, REJECTED
    }
}
