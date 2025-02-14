package online.muydinov.quizletclone.controller;

import lombok.RequiredArgsConstructor;
import online.muydinov.quizletclone.dto.CardDTO;
import online.muydinov.quizletclone.entity.Card;
import online.muydinov.quizletclone.service.CardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cards")
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;

    @PostMapping("/{cardSetId}")
    public ResponseEntity<String> createCard(@PathVariable(name = "cardSetId") Long cardSetId, @RequestBody CardDTO cardDTO) {
        cardService.createCard(cardDTO,cardSetId);
        return ResponseEntity.status(HttpStatus.CREATED).body("Card Created");
    }

    @GetMapping("/allCards/{cardSetId}")
    public ResponseEntity<List<Card>> getAllCardsByCardSetId(@PathVariable(name = "cardSetId") Long cardSetId) {
        List<Card> card = cardService.getAllCardsByCardSetId(cardSetId);
        return ResponseEntity.ok(card);
    }

    @GetMapping("/{cardId}")
    public ResponseEntity<Card> getCardById(@PathVariable(name = "cardId") Long cardId) {
        Card card = cardService.getCardById(cardId);
        return ResponseEntity.ok(card);
    }

    @DeleteMapping("/{cardId}")
    public ResponseEntity<String> deleteCard(@PathVariable(name = "cardId") Long cardId) {
        cardService.deleteCardById(cardId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Card Deleted");
    }

    @PutMapping("/{cardId}")
    public ResponseEntity<Card> updateCard(@PathVariable(name = "cardId") Long cardId, @RequestBody CardDTO cardDTO) {
        cardDTO.setId(cardId);
        Card updatedCard = cardService.updateCard(cardDTO);
        return ResponseEntity.ok(updatedCard);
    }
}
