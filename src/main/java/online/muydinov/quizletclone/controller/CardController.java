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

//    @GetMapping("/{cardId}")
//    public ResponseEntity<CardDTO> getCardById(@PathVariable Long cardId) {
//        Card cardSet = cardService.getCardById(cardId);
//        CardDTO dto = cardService.convertToDTO(cardSet);
//        return ResponseEntity.ok(dto);
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<String> deleteCard(@PathVariable Long id) {
//        cardService.deleteCard(id);
//        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Card Set Deleted");
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<CardDTO> updateCard(@PathVariable Long id, @RequestBody CardDTO cardDTO) {
//        Card updatedCard = cardService.updateCard(id, cardDTO);
//        CardDTO dto = cardService.convertToDTO(updatedCard);
//        return ResponseEntity.ok(dto);
//    }
}
