package online.muydinov.quizletclone.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class FlashCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstFlash;
    private String secondFlash;

    @ManyToOne
    @JoinColumn(name = "set_id", nullable = false)
    private FlashcardSet flashcardSet;

    @ManyToOne
    @JoinColumn(name = "first_language_id", nullable = false)
    private Language firstLanguage;

    @ManyToOne
    @JoinColumn(name = "second_language_id", nullable = false)
    private Language secondLanguage;
}
