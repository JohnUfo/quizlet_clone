package online.muydinov.quizletclone.repository;

import online.muydinov.quizletclone.record.UserRecord;
import online.muydinov.quizletclone.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    @Query("SELECT u.id FROM Users u where u.username = :username")
    Long getUserIdByUsername(@Param("username") String username);

    @Query("SELECT new online.muydinov.quizletclone.record.UserRecord(u.id, u.fullName, u.username) " +
            "FROM Users u WHERE u.username = :username")
    Optional<UserRecord> findUserRecordByUsername(@Param("username") String username);
}
