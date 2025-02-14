package online.muydinov.quizletclone.repository;

import online.muydinov.quizletclone.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Long> {
}
