package online.muydinov.quizletclone.service;

import lombok.RequiredArgsConstructor;
import online.muydinov.quizletclone.record.CardRecord;
import online.muydinov.quizletclone.entity.Card;
import online.muydinov.quizletclone.entity.CardSet;
import online.muydinov.quizletclone.repository.CardRepository;
import online.muydinov.quizletclone.repository.CardSetRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CardService {

    private final CardRepository cardRepository;
    private final CardSetRepository cardSetRepository;

    public List<CardRecord> getAllCardsByCardSetId(Long cardSetId) {
        return cardRepository.findByCardSetId(cardSetId).stream()
                .map(this::convertToRecord)
                .collect(Collectors.toList());
    }

    public CardRecord addCard(Long cardSetId, CardRecord CardRecord) {
        CardSet cardSet = cardSetRepository.findById(cardSetId)
                .orElseThrow(() -> new RuntimeException("CardSet not found"));

        Card card = new Card(null,CardRecord.term(),CardRecord.definition(),cardSet);
        Card savedCard = cardRepository.save(card);
        return convertToRecord(savedCard);
    }

    public CardRecord updateCard(Long cardId, CardRecord CardRecord) {
        Optional<Card> optionalCard = cardRepository.findById(cardId);
        if (optionalCard.isPresent()) {
            Card card = optionalCard.get();
            card.setTerm(CardRecord.term());
            card.setDefinition(CardRecord.definition());
            Card updatedCard = cardRepository.save(card);
            return convertToRecord(updatedCard);
        } else {
            throw new RuntimeException("Card not found with id: " + cardId);
        }
    }

    private CardRecord convertToRecord(Card card) {
        return new CardRecord(card.getId(), card.getTerm(), card.getDefinition());
    }
}