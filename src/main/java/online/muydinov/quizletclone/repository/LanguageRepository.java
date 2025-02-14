package online.muydinov.quizletclone.repository;

import online.muydinov.quizletclone.entity.Language;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LanguageRepository extends JpaRepository<Language, Long> {
}
