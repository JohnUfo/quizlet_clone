package online.muydinov.quizletclone.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @ManyToOne(fetch = FetchType.LAZY)  // Use LAZY loading
    @JoinColumn(name = "set_id", nullable = false)
    @JsonIgnore
    private CardSet cardSet;
}
