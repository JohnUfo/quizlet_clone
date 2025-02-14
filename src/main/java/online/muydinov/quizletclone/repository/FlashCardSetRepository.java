package online.muydinov.quizletclone.repository;

import online.muydinov.quizletclone.entity.FlashcardSet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlashCardSetRepository extends JpaRepository<FlashcardSet, Long> {
}
