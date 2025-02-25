package online.muydinov.quizletclone.repository;

import online.muydinov.quizletclone.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    List<Card> findByCardSetId(Long cardSetId);
}