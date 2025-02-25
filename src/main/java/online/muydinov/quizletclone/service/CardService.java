package online.muydinov.quizletclone.service;

import lombok.RequiredArgsConstructor;
import online.muydinov.quizletclone.dto.CardDTO;
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

    public List<CardDTO> getAllCardsByCardSetId(Long cardSetId) {
        return cardRepository.findByCardSetId(cardSetId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public CardDTO addCard(Long cardSetId, CardDTO cardDTO) {
        CardSet cardSet = cardSetRepository.findById(cardSetId)
                .orElseThrow(() -> new RuntimeException("CardSet not found"));

        Card card = new Card();
        card.setTerm(cardDTO.getTerm());
        card.setDefinition(cardDTO.getDefinition());
        card.setCardSet(cardSet);

        Card savedCard = cardRepository.save(card);
        return convertToDTO(savedCard);
    }

    public CardDTO updateCard(Long cardId, CardDTO cardDTO) {
        Optional<Card> optionalCard = cardRepository.findById(cardId);
        if (optionalCard.isPresent()) {
            Card card = optionalCard.get();
            card.setTerm(cardDTO.getTerm());
            card.setDefinition(cardDTO.getDefinition());
            Card updatedCard = cardRepository.save(card);
            return convertToDTO(updatedCard);
        } else {
            throw new RuntimeException("Card not found with id: " + cardId);
        }
    }

    private CardDTO convertToDTO(Card card) {
        return new CardDTO(card.getId(), card.getTerm(), card.getDefinition());
    }
}