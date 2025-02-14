package online.muydinov.quizletclone.controller;

import lombok.RequiredArgsConstructor;
import online.muydinov.quizletclone.dto.CardSetDTO;
import online.muydinov.quizletclone.entity.CardSet;
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
    public ResponseEntity<Void> deleteCardSet(@PathVariable Long cardSetId) {
        cardSetService.deleteCardSet(cardSetId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{cardSetId}")
    public ResponseEntity<CardSetDTO> updateCardSet(@PathVariable Long cardSetId, @RequestBody CardSetDTO cardSetDTO) {
        return ResponseEntity.ok(cardSetService.updateCardSet(cardSetId, cardSetDTO));
    }
}