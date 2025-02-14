package online.muydinov.quizletclone.controller;

import lombok.RequiredArgsConstructor;
import online.muydinov.quizletclone.dto.FlashCardSetDTO;
import online.muydinov.quizletclone.entity.FlashcardSet;
import online.muydinov.quizletclone.service.FlashCardSetService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/flashcardsets")
@RequiredArgsConstructor
public class FlashCardSetController {

    private final FlashCardSetService flashcardSetService;

    @PostMapping
    public ResponseEntity<String> createFlashcardSet(@RequestBody FlashCardSetDTO flashcardSetDTO) {
        return flashcardSetService.createFlashcardSet(flashcardSetDTO);
    }

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<FlashCardSetDTO>> getAllFlashcardSets() {
        List<FlashCardSetDTO> flashCardSets = flashcardSetService.getAllFlashcardSetsDTO();
        return ResponseEntity.ok(flashCardSets);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<FlashCardSetDTO> getFlashcardSetById(@PathVariable Long id) {
        FlashcardSet flashcardSet = flashcardSetService.getFlashcardSetById(id);
        FlashCardSetDTO dto = flashcardSetService.convertToDTO(flashcardSet);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> deleteFlashcardSet(@PathVariable Long id) {
        flashcardSetService.deleteFlashcardSet(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Flashcard Set Deleted");
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<FlashCardSetDTO> updateFlashcardSet(@PathVariable Long id, @RequestBody FlashCardSetDTO flashCardSetDTO) {
        FlashcardSet updatedFlashcardSet = flashcardSetService.updateFlashcardSet(id, flashCardSetDTO);
        FlashCardSetDTO dto = flashcardSetService.convertToDTO(updatedFlashcardSet);
        return ResponseEntity.ok(dto);
    }
}
