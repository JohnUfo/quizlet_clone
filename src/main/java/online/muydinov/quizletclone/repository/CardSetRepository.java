package online.muydinov.quizletclone.repository;

import online.muydinov.quizletclone.dto.CardSetDTO;
import online.muydinov.quizletclone.entity.CardSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CardSetRepository extends JpaRepository<CardSet, Long> {

    @Query("SELECT new online.muydinov.quizletclone.dto.CardSetDTO(" +
            "c.id, c.name, c.isPublic, c.firstLanguage, c.secondLanguage, " +
            "c.creator.id, c.creator.username, " +
            "CASE " +
            "   WHEN c.creator.id = :currentUserId THEN 'OWNER' " + // Ensure the user sees their own sets
            "   WHEN :currentUserId IN (SELECT u.id FROM c.approvedUsers u) THEN 'ACCESSIBLE' " +
            "   WHEN ar.status = 'PENDING' THEN 'PENDING' " +
            "   WHEN ar.status = 'DECLINED' THEN 'DECLINED' " +
            "   ELSE 'PUBLIC' " +
            "END) " +
            "FROM CardSet c " +
            "LEFT JOIN AccessRequest ar ON ar.cardSet.id = c.id AND ar.requester.id = :currentUserId " +
            "WHERE c.isPublic = true OR c.creator.id = :currentUserId " +
            "OR :currentUserId IN (SELECT u.id FROM c.approvedUsers u) OR ar IS NOT NULL")
    List<CardSetDTO> findAllPublicAndAccessibleCardsets(@Param("currentUserId") Long currentUserId);

    @Query("SELECT c FROM CardSet c WHERE c.id = :setId AND c.creator.username = :username")
    Optional<CardSet> findByIdAndOwner(Long setId, String username);

    @Query("SELECT c.creator.username FROM CardSet c WHERE c.id = :cardSetId")
    Optional<String> findOwnerUsernameByCardSetId(@Param("cardSetId") Long cardSetId);

}