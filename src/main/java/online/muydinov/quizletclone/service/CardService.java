package online.muydinov.quizletclone.service;

import lombok.RequiredArgsConstructor;
import online.muydinov.quizletclone.dto.CardDTO;
import online.muydinov.quizletclone.entity.Card;
import online.muydinov.quizletclone.entity.CardSet;
import online.muydinov.quizletclone.exceptions.CardNotFoundException;
import online.muydinov.quizletclone.exceptions.CardSetNotFoundException;
import online.muydinov.quizletclone.repository.CardRepository;
import online.muydinov.quizletclone.repository.CardSetRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CardService {

    private final CardRepository cardRepository;
    private final CardSetRepository cardSetRepository;

    public void createCard(CardDTO cardDTO, Long cardSetId) {
        CardSet cardSet = cardSetRepository.findById(cardSetId)
                .orElseThrow(() -> new CardSetNotFoundException("CardSet with ID " + cardSetId + " not found"));

        Card card = new Card(null, cardDTO.getFirstCard(), cardDTO.getSecondCard(), cardSet);
        cardRepository.save(card);
    }

    public Card getCardById(Long cardId) {
        return cardRepository.findById(cardId)
                .orElseThrow(() -> new CardNotFoundException("Card not found"));
    }

    public void deleteCardById(Long cardId) {
        if (!cardRepository.existsById(cardId)) {
            throw new CardNotFoundException("Card not found");
        }
        cardRepository.deleteById(cardId);
    }

    public Card updateCard(CardDTO cardDTO) {
        Card existingCard = cardRepository.findById(cardDTO.getId())
                .orElseThrow(() -> new CardNotFoundException("Card not found"));

        existingCard.setFirstCard(cardDTO.getFirstCard());
        existingCard.setSecondCard(cardDTO.getSecondCard());
        return cardRepository.save(existingCard);
    }

    public List<Card> getAllCardsByCardSetId(Long cardSetId) {
        return cardRepository.findAllByCardSet_Id(cardSetId);
    }

    public List<CardDTO> convertCardToDTO(List<Card> cardList) {
        return cardList.stream()
                .map(card -> new CardDTO(card.getId(), card.getFirstCard(), card.getSecondCard()))
                .collect(Collectors.toList());
    }
}
