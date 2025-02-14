package online.muydinov.quizletclone.repository;

import online.muydinov.quizletclone.entity.CardSet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardSetRepository extends JpaRepository<CardSet, Long> {
    boolean existsByName(String name);
}
