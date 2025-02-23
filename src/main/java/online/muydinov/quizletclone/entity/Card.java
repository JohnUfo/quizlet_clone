package online.muydinov.quizletclone.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Card {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String firstCard;
    private String secondCard;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "set_id", nullable = false)
    @JsonBackReference
    private CardSet cardSet;
}
