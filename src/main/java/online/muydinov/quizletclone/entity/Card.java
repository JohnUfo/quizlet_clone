package online.muydinov.quizletclone.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstCard;
    private String secondCard;

    @ManyToOne
    @JoinColumn(name = "set_id", nullable = false)
    private CardSet cardSet;
}
