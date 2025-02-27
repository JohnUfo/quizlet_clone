package online.muydinov.quizletclone.controller;

import online.muydinov.quizletclone.record.CardSetRecord;
import online.muydinov.quizletclone.service.CardSetService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cardsets")
public class CardSetController {

    private final CardSetService cardSetService;

    public CardSetController(CardSetService cardSetService) {
        this.cardSetService = cardSetService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<CardSetRecord>> getAllCardSets() {
        List<CardSetRecord> allCardSets = cardSetService.getAllCardSets();
        return ResponseEntity.ok(allCardSets);
    }

    @PostMapping
    public ResponseEntity<CardSetRecord> createCardSet(@RequestBody CardSetRecord cardSetRecord) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(cardSetService.createCardSet(cardSetRecord));
    }

    @GetMapping("/{cardSetId}")
    public ResponseEntity<CardSetRecord> getCardSetById(@PathVariable Long cardSetId) {
        return ResponseEntity.ok(cardSetService.getCardSetById(cardSetId));
    }

    @DeleteMapping("/{cardSetId}")
    public ResponseEntity<Void> deleteCardSet(@PathVariable Long cardSetId) {
        cardSetService.deleteCardSet(cardSetId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{cardSetId}")
    public ResponseEntity<CardSetRecord> updateCardSet(@PathVariable Long cardSetId, @RequestBody CardSetRecord cardSetRecord) {
        return ResponseEntity.ok(cardSetService.updateCardSet(cardSetId, cardSetRecord));
    }
}