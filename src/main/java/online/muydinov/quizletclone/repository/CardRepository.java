package online.muydinov.quizletclone.repository;

import online.muydinov.quizletclone.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CardRepository extends JpaRepository<Card, Long> {
    List<Card> findAllByCardSet_Id(Long cardSetId);
}
