package online.muydinov.quizletclone.service;

import lombok.RequiredArgsConstructor;
import online.muydinov.quizletclone.dto.CardSetDTO;
import online.muydinov.quizletclone.entity.CardSet;
import online.muydinov.quizletclone.entity.User;
import online.muydinov.quizletclone.enums.Language;
import online.muydinov.quizletclone.exceptions.CardSetAlreadyExistsException;
import online.muydinov.quizletclone.exceptions.CardSetNotFoundException;
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

    public CardSet createCardSet(CardSetDTO cardSetDTO) {
        if (cardSetRepository.existsByName(cardSetDTO.getName())) {
            throw new CardSetAlreadyExistsException("This Set already exists");
        }

        String username = myUserDetailsService.getUsername();
        User creator = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        CardSet cardSet = new CardSet();
        cardSet.setName(cardSetDTO.getName());
        cardSet.setPublic(cardSetDTO.isPublic());
        cardSet.setCreator(creator);
        cardSet.setFirstLanguage(Language.valueOf(cardSetDTO.getFirstLanguage()));
        cardSet.setSecondLanguage(Language.valueOf(cardSetDTO.getSecondLanguage()));

        return cardSetRepository.save(cardSet);
    }

    public List<CardSetDTO> getAllCardSetsDTO() {
        return cardSetRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public CardSetDTO getCardSetById(Long id) {
        CardSet cardSet = findCardSetByIdAndVerifyOwner(id);
        return convertToDTO(cardSet);
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

        return convertToDTO(cardSetRepository.save(cardSet));
    }

    private CardSet findCardSetByIdAndVerifyOwner(Long id) {
        String username = myUserDetailsService.getUsername();
        CardSet cardSet = cardSetRepository.findById(id)
                .orElseThrow(() -> new CardSetNotFoundException("Card Set not found"));

        if (!cardSet.getCreator().getUsername().equals(username)) {
            throw new UnauthorizedAccessException("You do not have permission to access this Card Set");
        }
        return cardSet;
    }

    public CardSetDTO convertToDTO(CardSet cardSet) {
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
