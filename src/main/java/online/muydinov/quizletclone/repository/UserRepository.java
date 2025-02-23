package online.muydinov.quizletclone.repository;

import online.muydinov.quizletclone.dto.UserDTO;
import online.muydinov.quizletclone.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    Long getUserIdByUsername(String username);

    @Query("SELECT new online.muydinov.quizletclone.dto.UserDTO(u.id, u.fullName, u.username) " +
                "FROM Users u WHERE u.username = :username")
    Optional<UserDTO> findUserDTOByUsername(@Param("username") String username);
}
