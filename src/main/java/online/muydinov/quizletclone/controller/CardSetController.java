package online.muydinov.quizletclone.controller;

import lombok.RequiredArgsConstructor;
import online.muydinov.quizletclone.dto.CardSetDTO;
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

    @GetMapping("/all")
    public ResponseEntity<List<CardSetDTO>> getAllCardSets() {
        List<CardSetDTO> allCardSets = cardSetService.getAllCardSets();
        return ResponseEntity.ok(allCardSets);
    }

    @PostMapping
    public ResponseEntity<CardSetDTO> createCardSet(@RequestBody CardSetDTO cardSetDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(cardSetService.createCardSet(cardSetDTO));
    }

    @GetMapping("/{cardSetId}")
    public ResponseEntity<CardSetDTO> getCardSetById(@PathVariable Long cardSetId) {
        return ResponseEntity.ok(cardSetService.getCardSetById(cardSetId));
    }

    @DeleteMapping("/{cardSetId}")
    public ResponseEntity<Void> deleteCardSet(@PathVariable Long cardSetId) {
        cardSetService.deleteCardSet(cardSetId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{cardSetId}")
    public ResponseEntity<CardSetDTO> updateCardSet(@PathVariable Long cardSetId, @RequestBody CardSetDTO cardSetDTO) {
        return ResponseEntity.ok(cardSetService.updateCardSet(cardSetId, cardSetDTO));
    }
}