package online.muydinov.quizletclone.controller;

import lombok.RequiredArgsConstructor;
import online.muydinov.quizletclone.dto.CardSetDTO;
import online.muydinov.quizletclone.dto.SetAccessRequestDTO;
import online.muydinov.quizletclone.entity.CardSet;
import online.muydinov.quizletclone.entity.SetAccessRequest;
import online.muydinov.quizletclone.service.CardSetService;
import online.muydinov.quizletclone.service.SetAccessRequestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cardsets")
@RequiredArgsConstructor
public class CardSetController {

    private final CardSetService cardSetService;
    private final SetAccessRequestService setAccessRequestService;

    @PostMapping
    public ResponseEntity<CardSetDTO> createCardSet(@RequestBody CardSetDTO cardSetDTO) {
        CardSet createdCardSet = cardSetService.createCardSet(cardSetDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(cardSetService.convertToDTO(createdCardSet));
    }

    @GetMapping
    public ResponseEntity<List<CardSetDTO>> getAllCardSets() {
        return ResponseEntity.ok(cardSetService.getAllCardSetsDTO());
    }

    @GetMapping("/{cardSetId}")
    public ResponseEntity<CardSetDTO> getCardSetById(@PathVariable Long cardSetId) {
        return ResponseEntity.ok(cardSetService.getCardSetById(cardSetId));
    }

    @DeleteMapping("/{cardSetId}")
    public ResponseEntity<Void> deleteCardSet(@PathVariable(name = "cardSetId") Long cardSetId) {
        cardSetService.deleteCardSet(cardSetId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{cardSetId}")
    public ResponseEntity<CardSetDTO> updateCardSet(@PathVariable(name = "cardSetId") Long cardSetId, @RequestBody CardSetDTO cardSetDTO) {
        return ResponseEntity.ok(cardSetService.updateCardSet(cardSetId, cardSetDTO));
    }

    // ACCESS REQUESTS
    @PostMapping("/{cardSetId}/request-access")
    public ResponseEntity<String> requestAccess(@PathVariable(name = "cardSetId") Long cardSetId) {
        return ResponseEntity.ok(setAccessRequestService.requestAccess(cardSetId));
    }

    @GetMapping("/{cardSetId}/requests")
    public ResponseEntity<List<SetAccessRequestDTO>> getPendingRequests(@PathVariable(name = "cardSetId") Long cardSetId) {
        return ResponseEntity.ok(setAccessRequestService.getPendingRequests(cardSetId));
    }

    @PutMapping("/{cardSetId}/requests/{requestId}")
    public ResponseEntity<String> respondToRequest(
            @PathVariable(name = "cardSetId") Long cardSetId,
            @PathVariable Long requestId,
            @RequestParam boolean approve) {
        return ResponseEntity.ok(setAccessRequestService.respondToRequest(cardSetId, requestId, approve));
    }
}