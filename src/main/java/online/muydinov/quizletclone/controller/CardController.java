package online.muydinov.quizletclone.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Card API", description = "Endpoints for managing cards")
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;

    @Operation(summary = "Create a New Card", description = "Adds a new card to the specified card set.")
    @PostMapping("/{cardSetId}")
    public ResponseEntity<String> createCard(
            @PathVariable(name = "cardSetId") Long cardSetId,
            @RequestBody CardDTO cardDTO) {
        cardService.createCard(cardDTO, cardSetId);
        return ResponseEntity.status(HttpStatus.CREATED).body("Card Created Successfully");
    }

    @Operation(summary = "Get All Cards in a Card Set", description = "Retrieves all cards belonging to the specified card set.")
    @GetMapping("/allCards/{cardSetId}")
    public ResponseEntity<List<Card>> getAllCardsByCardSetId(@PathVariable Long cardSetId) {
        List<Card> cards = cardService.getAllCardsByCardSetId(cardSetId);
        return ResponseEntity.ok(cards);
    }

    @Operation(summary = "Get Card by ID", description = "Fetches details of a specific card by its ID.")
    @GetMapping("/{cardId}")
    public ResponseEntity<Card> getCardById(@PathVariable Long cardId) {
        Card card = cardService.getCardById(cardId);
        return ResponseEntity.ok(card);
    }

    @Operation(summary = "Delete a Card", description = "Removes a card from the database using its ID.")
    @DeleteMapping("/{cardId}")
    public ResponseEntity<Void> deleteCard(@PathVariable Long cardId) {
        cardService.deleteCardById(cardId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Update an Existing Card", description = "Modifies details of an existing card using its ID.")
    @PutMapping("/{cardId}")
    public ResponseEntity<Card> updateCard(
            @PathVariable Long cardId,
            @RequestBody CardDTO cardDTO) {
        cardDTO.setId(cardId);
        Card updatedCard = cardService.updateCard(cardDTO);
        return ResponseEntity.ok(updatedCard);
    }
}