package online.muydinov.quizletclone.controller;

import lombok.RequiredArgsConstructor;
import online.muydinov.quizletclone.dto.CardSetDTO;
import online.muydinov.quizletclone.service.AccessRequestService;
import online.muydinov.quizletclone.service.CardSetService;
import online.muydinov.quizletclone.service.JWTService;
import online.muydinov.quizletclone.service.MyUserDetailsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cardsets")
@RequiredArgsConstructor
public class CardSetController {

    private final CardSetService cardSetService;
    private final AccessRequestService AccessRequestService;
    private final JWTService jwtService;
    private final MyUserDetailsService myUserDetailsService;

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
//
//    @GetMapping("/{cardSetId}")
//    public ResponseEntity<CardSetDTO> getCardSetById(@PathVariable Long cardSetId) {
//        return ResponseEntity.ok(cardSetService.getCardSetById(cardSetId, myUserDetailsService.getUsername()));
//    }
//
//    @DeleteMapping("/{cardSetId}")
//    public ResponseEntity<Void> deleteCardSet(@PathVariable Long cardSetId) {
//        cardSetService.deleteCardSet(cardSetId);
//        return ResponseEntity.noContent().build();
//    }
//
//    @PutMapping("/{cardSetId}")
//    public ResponseEntity<CardSetDTO> updateCardSet(@PathVariable Long cardSetId, @RequestBody CardSetDTO cardSetDTO) {
//        return ResponseEntity.ok(cardSetService.updateCardSet(cardSetId, cardSetDTO));
//    }
//
//    @PostMapping("/{cardSetId}/request-access")
//    public ResponseEntity<String> requestAccess(@PathVariable Long cardSetId) {
//        return ResponseEntity.ok(AccessRequestService.requestAccess(cardSetId));
//    }
//
//    @GetMapping("/{cardSetId}/requests")
//    public ResponseEntity<List<AccessRequestDTO>> getPendingRequests(
//            @PathVariable Long cardSetId) {
//        List<AccessRequestDTO> pendingRequests = AccessRequestService.getPendingRequests(cardSetId, myUserDetailsService.getUsername());
//        return ResponseEntity.ok(pendingRequests);
//    }
//
//    @PutMapping("/{cardSetId}/requests/{requestId}")
//    public ResponseEntity<Response> respondToRequest(
//            @PathVariable Long cardSetId,
//            @PathVariable Long requestId,
//            @RequestParam boolean approve) {
//        Response response = AccessRequestService.respondToRequest(cardSetId, requestId, approve);
//        return ResponseEntity.ok(response);
//    }
}