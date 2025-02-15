package online.muydinov.quizletclone.service;

import lombok.RequiredArgsConstructor;
import online.muydinov.quizletclone.dto.CardSetDTO;
import online.muydinov.quizletclone.dto.CardSetWithCardsDTO;
import online.muydinov.quizletclone.entity.CardSet;
import online.muydinov.quizletclone.entity.User;
import online.muydinov.quizletclone.enums.Language;
import online.muydinov.quizletclone.exceptions.CardSetAlreadyExistsException;
import online.muydinov.quizletclone.exceptions.UnauthorizedAccessException;
import online.muydinov.quizletclone.exceptions.UserNotFoundException;
import online.muydinov.quizletclone.repository.CardSetRepository;
import online.muydinov.quizletclone.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CardSetService {

    private final UserRepository userRepository;
    private final MyUserDetailsService myUserDetailsService;
    private final CardSetRepository cardSetRepository;
    private final CardService cardService;

    public CardSetDTO createCardSet(CardSetDTO cardSetDTO) {
        if (cardSetRepository.existsByName(cardSetDTO.getName())) {
            throw new CardSetAlreadyExistsException("This set already exists.");
        }

        User creator = userRepository.findByUsername(myUserDetailsService.getUsername())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        CardSet cardSet = new CardSet();
        cardSet.setName(cardSetDTO.getName());
        cardSet.setPublic(cardSetDTO.isPublic());
        cardSet.setCreator(creator);
        cardSet.setFirstLanguage(Language.valueOf(cardSetDTO.getFirstLanguage()));
        cardSet.setSecondLanguage(Language.valueOf(cardSetDTO.getSecondLanguage()));

        return convertCardSetToDTO(cardSetRepository.save(cardSet));
    }

    public List<CardSetWithCardsDTO> getAllCardSets() {
        return cardSetRepository.findAll().stream()
                .map(this::convertCardSetWithCardsToDTO)
                .collect(Collectors.toList());
    }

    public CardSetWithCardsDTO getCardSetById(Long id) {
        return convertCardSetWithCardsToDTO(findCardSetByIdAndVerifyOwner(id));
    }

    public void deleteCardSet(Long id) {
        CardSet cardSet = findCardSetByIdAndVerifyOwner(id);
        cardSetRepository.delete(cardSet);
    }

    public CardSetDTO updateCardSet(Long id, CardSetDTO cardSetDTO) {
        CardSet cardSet = findCardSetByIdAndVerifyOwner(id);

        cardSet.setName(cardSetDTO.getName());
        cardSet.setPublic(cardSetDTO.isPublic());
        cardSet.setFirstLanguage(Language.valueOf(cardSetDTO.getFirstLanguage()));
        cardSet.setSecondLanguage(Language.valueOf(cardSetDTO.getSecondLanguage()));

        return convertCardSetToDTO(cardSetRepository.save(cardSet));
    }

    private CardSet findCardSetByIdAndVerifyOwner(Long id) {
        String username = myUserDetailsService.getUsername();
        return cardSetRepository.findById(id)
                .filter(cardSet -> cardSet.getCreator().getUsername().equals(username))
                .orElseThrow(() -> new UnauthorizedAccessException("Access denied or Card Set not found"));
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
}
