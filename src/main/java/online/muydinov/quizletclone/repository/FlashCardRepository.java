package online.muydinov.quizletclone.repository;

import online.muydinov.quizletclone.entity.FlashCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlashCardRepository extends JpaRepository<FlashCard, Long> {
}
