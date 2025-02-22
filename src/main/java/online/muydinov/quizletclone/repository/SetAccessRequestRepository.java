package online.muydinov.quizletclone.repository;

import online.muydinov.quizletclone.entity.CardSet;
import online.muydinov.quizletclone.entity.SetAccessRequest;
import online.muydinov.quizletclone.entity.User;
import online.muydinov.quizletclone.enums.RequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SetAccessRequestRepository extends JpaRepository<SetAccessRequest, Long> {
    Optional<SetAccessRequest> findByRequesterAndCardSet(User requester, CardSet cardSet);

    @Query("SELECT r FROM SetAccessRequest r WHERE r.cardSet.id = :cardSetId AND r.status = :status")
    List<SetAccessRequest> findPendingRequestsByCardSetId(@Param("cardSetId") Long cardSetId, @Param("status") RequestStatus status);

}
