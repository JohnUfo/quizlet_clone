package online.muydinov.quizletclone.repository;

import online.muydinov.quizletclone.entity.AccessRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AccessRequestRepository extends JpaRepository<AccessRequest, Long> {

    @Query("SELECT r FROM AccessRequest r WHERE r.cardSet.id = :cardSetId AND r.status = :status")
    List<AccessRequest> findPendingRequestsByCardSetId(@Param("cardSetId") Long cardSetId, @Param("status") String status);

}