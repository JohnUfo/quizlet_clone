package online.muydinov.quizletclone.repository;

import online.muydinov.quizletclone.entity.CardSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CardSetRepository extends JpaRepository<CardSet, Long> {
    boolean existsByName(String name);

    @Query("SELECT c.id FROM CardSet c WHERE c.id = :setId")
    Optional<Long> findCardSetIdById(Long setId);
}
