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
            return ResponseEntity.status(HttpStatus.CONFLICT).body("This Set already exists");
        }

        String username = myUserDetailsService.getUsername();
        User creator = userRepository.findByUsername(username);

        if (creator == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Creator not found");
        }

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
        return flashcardSetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Flashcard Set not found"));
    }

    public ResponseEntity<String> deleteFlashcardSet(Long id) {
        FlashcardSet flashcardSet = flashcardSetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Flashcard Set not found"));

        flashcardSetRepository.delete(flashcardSet);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Set Deleted");
    }

    public FlashcardSet updateFlashcardSet(Long id, FlashCardSetDTO flashCardSetDTO) {
        FlashcardSet existingFlashcardSet = flashcardSetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Flashcard Set not found"));

        existingFlashcardSet.setName(flashCardSetDTO.getName());
        existingFlashcardSet.setPublic(flashCardSetDTO.isPublic());

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
