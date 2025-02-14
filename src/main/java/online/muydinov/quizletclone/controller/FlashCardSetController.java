package online.muydinov.quizletclone.controller;

import lombok.RequiredArgsConstructor;
import online.muydinov.quizletclone.dto.FlashCardSetDTO;
import online.muydinov.quizletclone.entity.FlashcardSet;
import online.muydinov.quizletclone.service.FlashCardSetService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/flashcardsets")
@RequiredArgsConstructor
public class FlashCardSetController {

    private final FlashCardSetService flashcardSetService;
    FlashCardSetDTO flashCardSetDTO;

    @PostMapping
    public ResponseEntity<String> createFlashcardSet(@RequestBody FlashCardSetDTO flashcardSetDTO) {
        return flashcardSetService.createFlashcardSet(flashcardSetDTO);
    }

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public List<FlashCardSetDTO> getAllFlashcardSets() {
        return flashcardSetService.getAllFlashcardSetsDTO();
    }


    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public FlashcardSet getFlashcardSetById(@PathVariable Long id) {
        return flashcardSetService.getFlashcardSetById(id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public void deleteFlashcardSet(@PathVariable Long id) {
        flashcardSetService.deleteFlashcardSet(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public FlashcardSet updateFlashcardSet(@PathVariable Long id, @RequestBody FlashcardSet flashcardSet) {
        return flashcardSetService.updateFlashcardSet(id, flashcardSet);
    }
}
