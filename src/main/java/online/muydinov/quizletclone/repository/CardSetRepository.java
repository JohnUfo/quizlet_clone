package online.muydinov.quizletclone.repository;

import online.muydinov.quizletclone.entity.CardSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CardSetRepository extends JpaRepository<CardSet, Long> {
    boolean existsByName(String name);

    @Query("SELECT c FROM CardSet c WHERE c.id = :setId AND c.creator.username = :username")
    Optional<CardSet> findByIdAndOwner(Long setId, String username);

    @Query("SELECT c.creator.username from CardSet c WHERE c.id =:setId")
    String findOwnerUsernameByCardSetId(Long setId);

    @Query("SELECT c FROM CardSet c WHERE c.creator.username = :username")
    List<CardSet> findByOwnersUsername(@Param("username") String username);
}