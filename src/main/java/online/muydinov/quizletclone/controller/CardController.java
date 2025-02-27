package online.muydinov.quizletclone.controller;

import lombok.RequiredArgsConstructor;
import online.muydinov.quizletclone.record.CardRecord;
import online.muydinov.quizletclone.service.CardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cards")
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;

    @GetMapping("/allCards/{cardSetId}")
    public ResponseEntity<List<CardRecord>> getAllCardsByCardSetId(@PathVariable Long cardSetId) {
        List<CardRecord> cards = cardService.getAllCardsByCardSetId(cardSetId);
        return ResponseEntity.ok(cards);
    }

    @PostMapping("/{cardSetId}")
    public ResponseEntity<CardRecord> addCard(@PathVariable Long cardSetId, @RequestBody CardRecord CardRecord) {
        CardRecord newCard = cardService.addCard(cardSetId, CardRecord);
        return ResponseEntity.ok(newCard);
    }

    @PutMapping("/{cardId}")
    public ResponseEntity<CardRecord> updateCard(@PathVariable Long cardId, @RequestBody CardRecord CardRecord) {
        CardRecord updatedCard = cardService.updateCard(cardId, CardRecord);
        return ResponseEntity.ok(updatedCard);
    }
}