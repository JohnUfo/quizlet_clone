package online.muydinov.quizletclone.repository;

import online.muydinov.quizletclone.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
