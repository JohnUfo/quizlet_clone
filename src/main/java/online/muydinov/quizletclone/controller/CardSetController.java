package online.muydinov.quizletclone.controller;

import lombok.RequiredArgsConstructor;
import online.muydinov.quizletclone.dto.CardSetDTO;
import online.muydinov.quizletclone.entity.CardSet;
import online.muydinov.quizletclone.service.CardSetService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cardsets")
@RequiredArgsConstructor
public class CardSetController {

    private final CardSetService cardSetService;

    @PostMapping
    public ResponseEntity<String> createCardSet(@RequestBody CardSetDTO cardSetDTO) {
        cardSetService.createCardSet(cardSetDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Card Set Created");
    }

    @GetMapping
    public ResponseEntity<List<CardSetDTO>> getAllCardSets() {
        List<CardSetDTO> cardSets = cardSetService.getAllCardSetsDTO();
        return ResponseEntity.ok(cardSets);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CardSetDTO> getCardSetById(@PathVariable Long id) {
        CardSet cardSet = cardSetService.getCardSetById(id);
        CardSetDTO dto = cardSetService.convertToDTO(cardSet);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCardSet(@PathVariable Long id) {
        cardSetService.deleteCardSet(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Card Set Deleted");
    }

    @PutMapping("/{id}")
    public ResponseEntity<CardSetDTO> updateCardSet(@PathVariable Long id, @RequestBody CardSetDTO cardSetDTO) {
        CardSet updatedCardSet = cardSetService.updateCardSet(id, cardSetDTO);
        CardSetDTO dto = cardSetService.convertToDTO(updatedCardSet);
        return ResponseEntity.ok(dto);
    }
}
