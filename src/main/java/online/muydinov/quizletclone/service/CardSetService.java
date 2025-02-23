package online.muydinov.quizletclone.service;

import lombok.RequiredArgsConstructor;
import online.muydinov.quizletclone.dto.CardSetDTO;
import online.muydinov.quizletclone.dto.CardSetWithCardsDTO;
import online.muydinov.quizletclone.entity.CardSet;
import online.muydinov.quizletclone.entity.User;
import online.muydinov.quizletclone.exceptions.CardSetNotFoundException;
import online.muydinov.quizletclone.exceptions.UnauthorizedAccessException;
import online.muydinov.quizletclone.exceptions.UserNotFoundException;
import online.muydinov.quizletclone.repository.CardSetRepository;
import online.muydinov.quizletclone.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CardSetService {

    private final UserRepository userRepository;
    private final MyUserDetailsService myUserDetailsService;
    private final CardSetRepository cardSetRepository;
    private final CardService cardService;

    public CardSetDTO createCardSet(CardSetDTO cardSetDTO) {
        User creator = userRepository.findByUsername(myUserDetailsService.getUsername())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        CardSet cardSet = new CardSet();
        cardSet.setName(cardSetDTO.getName());
        cardSet.setPublic(cardSetDTO.getIsPublic());
        cardSet.setCreator(creator);
        cardSet.setFirstLanguage(cardSetDTO.getFirstLanguage());
        cardSet.setSecondLanguage(cardSetDTO.getSecondLanguage());

        return convertCardSetToDTO(cardSetRepository.save(cardSet));
    }

    public CardSetWithCardsDTO getCardSetById(Long id, String username) {
        CardSet cardSet = cardSetRepository.findById(id)
                .orElseThrow(() -> new CardSetNotFoundException("Card Set not found"));

        if (!hasAccessToCardSet(id, username)) {
            throw new UnauthorizedAccessException("You do not have access to this card set");
        }

        return convertCardSetWithCardsToDTO(cardSet);
    }

    public void deleteCardSet(Long id) {
        CardSet cardSet = findCardSetByIdAndVerifyOwner(id);
        cardSetRepository.delete(cardSet);
    }

    public CardSetDTO updateCardSet(Long id, CardSetDTO cardSetDTO) {
        CardSet cardSet = findCardSetByIdAndVerifyOwner(id);

        cardSet.setName(cardSetDTO.getName());
        cardSet.setPublic(cardSetDTO.getIsPublic());
        cardSet.setFirstLanguage(cardSetDTO.getFirstLanguage());
        cardSet.setSecondLanguage(cardSetDTO.getSecondLanguage());

        return convertCardSetToDTO(cardSetRepository.save(cardSet));
    }

    private CardSet findCardSetByIdAndVerifyOwner(Long id) {
        return cardSetRepository.findByIdAndOwner(id, myUserDetailsService.getUsername())
                .orElseThrow(() -> new UnauthorizedAccessException("Access denied or Card Set not found"));
    }

    public boolean hasAccessToCardSet(Long cardSetId, String username) {
        CardSet cardSet = cardSetRepository.findById(cardSetId)
                .orElseThrow(() -> new CardSetNotFoundException("Card Set not found"));

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        return cardSet.getCreator().equals(user) || cardSet.getApprovedUsers().contains(user);
    }


    private CardSetWithCardsDTO convertCardSetWithCardsToDTO(CardSet cardSet) {
        return new CardSetWithCardsDTO(
                cardSet.getId(),
                cardSet.getName(),
                cardSet.isPublic(),
                cardSet.getFirstLanguage().toString(),
                cardSet.getSecondLanguage().toString(),
                cardSet.getCreator().getId(),
                cardService.convertCardToDTO(cardSet.getCards())
        );
    }

    private CardSetDTO convertCardSetToDTO(CardSet cardSet) {
        return new CardSetDTO(
                cardSet.getId(),
                cardSet.getName(),
                cardSet.isPublic(),
                cardSet.getFirstLanguage().toString(),
                cardSet.getSecondLanguage().toString(),
                cardSet.getCreator().getId()
        );
    }

    public List<CardSetDTO> getCardSetsByUsername(String username) {
        List<CardSetDTO> cardSetDTOS = new ArrayList<>();
        for (CardSet cardSet : cardSetRepository.findByOwnersUsername(username)) {
            CardSetDTO dto = convertCardSetToDTO(cardSet);
            cardSetDTOS.add(dto);
        }
        return cardSetDTOS;
    }

    public List<CardSetDTO> getAllCardSets() {
        List<CardSetDTO> cardSetDTOS = new ArrayList<>();
        for (CardSet cardSet : cardSetRepository.findAll()) {
            CardSetDTO dto = convertCardSetToDTO(cardSet);
            cardSetDTOS.add(dto);
        }
        return cardSetDTOS;
    }
}
