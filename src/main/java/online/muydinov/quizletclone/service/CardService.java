package online.muydinov.quizletclone.service;

import lombok.RequiredArgsConstructor;
import online.muydinov.quizletclone.dto.CardDTO;
import online.muydinov.quizletclone.entity.Card;
import online.muydinov.quizletclone.entity.CardSet;
import online.muydinov.quizletclone.exceptions.CardSetNotFoundException;
import online.muydinov.quizletclone.repository.CardRepository;
import online.muydinov.quizletclone.repository.CardSetRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CardService {

    private final CardRepository cardRepository;
    private final CardSetRepository cardSetRepository;

    public void createCard(CardDTO cardDTO, Long cardSetId) {
        if (!cardSetRepository.existsById(cardSetId)) {
            throw new CardSetNotFoundException("CardSet with ID " + cardSetId + " not found");
        }

        CardSet cardSetRef = new CardSet();
        cardSetRef.setId(cardSetId);
        cardRepository.save(new Card(null, cardDTO.getFirstCard(), cardDTO.getSecondCard(), cardSetRef));
    }

    public Card getCardById(Long cardId) {
        return cardRepository.findById(cardId)
                .orElseThrow(() -> new CardSetNotFoundException("Card not found"));
    }

    public void deleteCardById(Long cardId) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new CardSetNotFoundException("Card not found"));

        cardRepository.delete(card);
    }

    public Card updateCard(CardDTO cardDTO) {
        Card existingCard = cardRepository.findById(cardDTO.getId())
                .orElseThrow(() -> new CardSetNotFoundException("Card not found"));

        existingCard.setFirstCard(cardDTO.getFirstCard());
        existingCard.setSecondCard(cardDTO.getSecondCard());
        return cardRepository.save(existingCard);
    }

    public List<Card> getAllCardsByCardSetId(Long cardSetId) {
        return cardRepository.findAllByCardSet_Id(cardSetId);
    }
}
