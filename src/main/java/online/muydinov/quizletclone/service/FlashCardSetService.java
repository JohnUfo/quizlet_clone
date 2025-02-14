package online.muydinov.quizletclone.service;

import lombok.RequiredArgsConstructor;
import online.muydinov.quizletclone.dto.FlashCardSetDTO;
import online.muydinov.quizletclone.entity.FlashcardSet;
import online.muydinov.quizletclone.entity.User;
import online.muydinov.quizletclone.repository.FlashCardSetRepository;
import online.muydinov.quizletclone.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FlashCardSetService {

    private final UserRepository userRepository;
    private final MyUserDetailsService myUserDetailsService;

    private final FlashCardSetRepository flashcardSetRepository;

    public ResponseEntity<String> createFlashcardSet(FlashCardSetDTO flashCardSetDTO) {
        boolean existsByName = flashcardSetRepository.existsByName(flashCardSetDTO.getName());
        if (existsByName) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("This Set already exist");
        }
        String username = myUserDetailsService.getUsername();
        User creator = userRepository.findByUsername(username);

        FlashcardSet flashcardSet = new FlashcardSet();
        flashcardSet.setName(flashCardSetDTO.getName());
        flashcardSet.setPublic(flashCardSetDTO.isPublic());
        flashcardSet.setCreator(creator);
        flashcardSetRepository.save(flashcardSet);
        return ResponseEntity.status(HttpStatus.CREATED).body("Set Created");
    }

    public List<FlashCardSetDTO> getAllFlashcardSetsDTO() {
        List<FlashcardSet> flashcardSets = flashcardSetRepository.findAll();
        return flashcardSets.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public FlashcardSet getFlashcardSetById(Long id) {
        return flashcardSetRepository.findById(id).orElseThrow(() -> new RuntimeException("Flashcard Set not found"));
    }

    public void deleteFlashcardSet(Long id) {
        flashcardSetRepository.deleteById(id);
    }

    public FlashcardSet updateFlashcardSet(Long id, FlashcardSet flashcardSet) {
        FlashcardSet existingFlashcardSet = flashcardSetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Flashcard Set not found"));
        existingFlashcardSet.setName(flashcardSet.getName());
        return flashcardSetRepository.save(existingFlashcardSet);
    }

    public FlashCardSetDTO convertToDTO(FlashcardSet flashcardSet) {
        FlashCardSetDTO dto = new FlashCardSetDTO();
        dto.setId(flashcardSet.getId());
        dto.setName(flashcardSet.getName());
        dto.setPublic(flashcardSet.isPublic());
        return dto;
    }
}
