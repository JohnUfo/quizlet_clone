package online.muydinov.quizletclone.controller;

import lombok.RequiredArgsConstructor;
import online.muydinov.quizletclone.dto.CardDTO;
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
    public ResponseEntity<List<CardDTO>> getAllCardsByCardSetId(@PathVariable Long cardSetId) {
        List<CardDTO> cards = cardService.getAllCardsByCardSetId(cardSetId);
        return ResponseEntity.ok(cards);
    }

    @PostMapping("/{cardSetId}")
    public ResponseEntity<CardDTO> addCard(@PathVariable Long cardSetId, @RequestBody CardDTO cardDTO) {
        CardDTO newCard = cardService.addCard(cardSetId, cardDTO);
        return ResponseEntity.ok(newCard);
    }

    @PutMapping("/{cardId}")
    public ResponseEntity<CardDTO> updateCard(@PathVariable Long cardId, @RequestBody CardDTO cardDTO) {
        CardDTO updatedCard = cardService.updateCard(cardId, cardDTO);
        return ResponseEntity.ok(updatedCard);
    }
}