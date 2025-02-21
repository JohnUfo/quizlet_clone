package online.muydinov.quizletclone.repository;

import online.muydinov.quizletclone.dto.CardDTO;
import online.muydinov.quizletclone.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CardRepository extends JpaRepository<Card, Long> {
    @Query("SELECT new online.muydinov.quizletclone.dto.CardDTO(c.id, c.firstCard, c.secondCard) " +
            "FROM Card c WHERE c.cardSet.id = :cardSetId")
    List<CardDTO> findAllByCardSet_Id(Long cardSetId);
}
