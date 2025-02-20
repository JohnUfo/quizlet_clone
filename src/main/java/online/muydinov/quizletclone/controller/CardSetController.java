package online.muydinov.quizletclone.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import online.muydinov.quizletclone.dto.CardSetDTO;
import online.muydinov.quizletclone.dto.CardSetWithCardsDTO;
import online.muydinov.quizletclone.dto.SetAccessRequestDTO;
import online.muydinov.quizletclone.service.CardSetService;
import online.muydinov.quizletclone.service.JWTService;
import online.muydinov.quizletclone.service.SetAccessRequestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cardsets")
@RequiredArgsConstructor
@Tag(name = "Card Set API", description = "Endpoints for managing card sets")
public class CardSetController {

    private final CardSetService cardSetService;
    private final SetAccessRequestService setAccessRequestService;
    private final JWTService jwtService;
    @Operation(summary = "Create a New Card Set", description = "Adds a new card set to the system.")
    @PostMapping
    public ResponseEntity<CardSetDTO> createCardSet(@RequestBody CardSetDTO cardSetDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(cardSetService.createCardSet(cardSetDTO));
    }

    @GetMapping("/my")
    public ResponseEntity<List<CardSetDTO>> getMyCardSets(@RequestHeader("Authorization") String token) {
        if (token.startsWith("Bearer ")) {
            token = token.substring(7); // Remove "Bearer " prefix
        }
        String username = jwtService.extractUserName(token);
        return ResponseEntity.ok(cardSetService.getCardSetsByUsername(username));
    }

    @Operation(summary = "Get Card Set by ID", description = "Fetches details of a specific card set using its ID.")
    @GetMapping("/{cardSetId}")
    public ResponseEntity<CardSetWithCardsDTO> getCardSetById(@PathVariable Long cardSetId) {
        return ResponseEntity.ok(cardSetService.getCardSetById(cardSetId));
    }

    @Operation(summary = "Delete a Card Set", description = "Removes a card set from the database using its ID.")
    @DeleteMapping("/{cardSetId}")
    public ResponseEntity<Void> deleteCardSet(@PathVariable Long cardSetId) {
        cardSetService.deleteCardSet(cardSetId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Update a Card Set", description = "Modifies an existing card set's details.")
    @PutMapping("/{cardSetId}")
    public ResponseEntity<CardSetDTO> updateCardSet(@PathVariable Long cardSetId, @RequestBody CardSetDTO cardSetDTO) {
        return ResponseEntity.ok(cardSetService.updateCardSet(cardSetId, cardSetDTO));
    }

    // ACCESS REQUESTS

    @Operation(summary = "Request Access to a Card Set", description = "Requests access to a private card set.")
    @PostMapping("/{cardSetId}/request-access")
    public ResponseEntity<String> requestAccess(@PathVariable Long cardSetId) {
        return ResponseEntity.ok(setAccessRequestService.requestAccess(cardSetId));
    }

    @Operation(summary = "Get Pending Access Requests", description = "Retrieves all pending access requests for a specific card set.")
    @GetMapping("/{cardSetId}/requests")
    public ResponseEntity<List<SetAccessRequestDTO>> getPendingRequests(@PathVariable Long cardSetId) {
        return ResponseEntity.ok(setAccessRequestService.getPendingRequests(cardSetId));
    }

    @Operation(summary = "Respond to Access Request", description = "Approves or rejects a user's access request to a card set.")
    @PutMapping("/{cardSetId}/requests/{requestId}")
    public ResponseEntity<String> respondToRequest(
            @PathVariable Long cardSetId,
            @PathVariable Long requestId,
            @RequestParam boolean approve) {
        return ResponseEntity.ok(setAccessRequestService.respondToRequest(cardSetId, requestId, approve));
    }
}