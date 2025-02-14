package online.muydinov.quizletclone.entity;

import jakarta.persistence.*;
import lombok.*;
import online.muydinov.quizletclone.enums.RequestStatus;

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
    private CardSet cardSet;

    @Enumerated(EnumType.STRING)
    private RequestStatus status;

}
