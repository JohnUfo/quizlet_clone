package online.muydinov.quizletclone.repository;

import online.muydinov.quizletclone.record.CardSetRecord;
import online.muydinov.quizletclone.entity.CardSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CardSetRepository extends JpaRepository<CardSet, Long> {

    @Query("SELECT new online.muydinov.quizletclone.record.CardSetRecord(" +
            "c.id, c.name, c.isPublic, c.firstLanguage, c.secondLanguage, " +
            "c.creator.id, c.creator.username, " +
            "CASE " +
            "   WHEN c.creator.id = :currentUserId THEN 'OWNER' " + // Current user is the creator
            "   WHEN :currentUserId IN (SELECT u.id FROM c.approvedUsers u) THEN 'ACCESSIBLE' " + // Current user has access
            "   WHEN EXISTS (SELECT 1 FROM AccessRequest ar WHERE ar.cardSet.id = c.id AND ar.requester.id = :currentUserId AND ar.status = 'PENDING') THEN 'PENDING' " + // Current user has a pending request
            "   WHEN EXISTS (SELECT 1 FROM AccessRequest ar WHERE ar.cardSet.id = c.id AND ar.requester.id = :currentUserId AND ar.status = 'DECLINED') THEN 'DECLINED' " + // Current user's request was declined
            "   ELSE 'PUBLIC' " + // Card set is public
            "END) " +
            "FROM CardSet c " +
            "WHERE c.creator.id = :currentUserId " + // Include card sets created by the current user
            "OR c.isPublic = true " + // Include public card sets
            "OR :currentUserId IN (SELECT u.id FROM c.approvedUsers u) " + // Include card sets where the current user is approved
            "OR EXISTS (SELECT 1 FROM AccessRequest ar WHERE ar.cardSet.id = c.id AND ar.requester.id = :currentUserId)")
    List<CardSetRecord> findAllPublicAndAccessibleCardsets(@Param("currentUserId") Long currentUserId);

    @Query("SELECT c FROM CardSet c WHERE c.id = :setId AND c.creator.username = :username")
    Optional<CardSet> findByIdAndOwner(Long setId, String username);

    @Query("SELECT c.creator.username FROM CardSet c WHERE c.id = :cardSetId")
    Optional<String> findOwnerUsernameByCardSetId(@Param("cardSetId") Long cardSetId);

    @Query("SELECT new online.muydinov.quizletclone.record.CardSetRecord(" +
            "c.id, c.name, c.isPublic, c.firstLanguage, c.secondLanguage, " +
            "c.creator.id, c.creator.username, " +
            "CASE " +
            "   WHEN c.creator.id = :currentUserId THEN 'OWNER' " + // Current user is the creator
            "   WHEN :currentUserId IN (SELECT u.id FROM c.approvedUsers u) THEN 'ACCESSIBLE' " + // Current user has access
            "   WHEN EXISTS (SELECT 1 FROM AccessRequest ar WHERE ar.cardSet.id = c.id AND ar.requester.id = :currentUserId AND ar.status = 'PENDING') THEN 'PENDING' " + // Current user has a pending request
            "   WHEN EXISTS (SELECT 1 FROM AccessRequest ar WHERE ar.cardSet.id = c.id AND ar.requester.id = :currentUserId AND ar.status = 'DECLINED') THEN 'DECLINED' " + // Current user's request was declined
            "   ELSE 'PUBLIC' " + // Card set is public
            "END) " +
            "FROM CardSet c " +
            "WHERE c.id = :cardSetId " + // Filter by cardSetId
            "AND (c.creator.id = :currentUserId " + // Include card sets created by the current user
            "OR c.isPublic = true " + // Include public card sets
            "OR :currentUserId IN (SELECT u.id FROM c.approvedUsers u) " + // Include card sets where the current user is approved
            "OR EXISTS (SELECT 1 FROM AccessRequest ar WHERE ar.cardSet.id = c.id AND ar.requester.id = :currentUserId))")
    CardSetRecord findPublicAndAccessibleCardsets(@Param("cardSetId") Long cardSetId, @Param("currentUserId") Long currentUserId);

    @Modifying
    @Query(value = "INSERT INTO accessible_sets (set_id, user_id) VALUES (?1, ?2)", nativeQuery = true)
    void addApprovedUser(Long cardSetId, Long userId);
}