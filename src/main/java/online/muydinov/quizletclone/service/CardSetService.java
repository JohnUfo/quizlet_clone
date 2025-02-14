package online.muydinov.quizletclone.service;

import lombok.RequiredArgsConstructor;
import online.muydinov.quizletclone.dto.CardSetDTO;
import online.muydinov.quizletclone.entity.CardSet;
import online.muydinov.quizletclone.entity.User;
import online.muydinov.quizletclone.enums.Language;
import online.muydinov.quizletclone.exceptions.CardSetAlreadyExistsException;
import online.muydinov.quizletclone.exceptions.CardSetNotFoundException;
import online.muydinov.quizletclone.exceptions.UserNotFoundException;
import online.muydinov.quizletclone.repository.CardSetRepository;
import online.muydinov.quizletclone.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CardSetService {

    private final UserRepository userRepository;
    private final MyUserDetailsService myUserDetailsService;
    private final CardSetRepository cardSetRepository;

    public CardSet createCardSet(CardSetDTO cardSetDTO) {
        boolean existsByName = cardSetRepository.existsByName(cardSetDTO.getName());
        if (existsByName) {
            throw new CardSetAlreadyExistsException("This Set already exists");
        }

        String username = myUserDetailsService.getUsername();
        User creator = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("Creator not found"));

        CardSet cardSet = new CardSet();
        cardSet.setName(cardSetDTO.getName());
        cardSet.setPublic(cardSetDTO.isPublic());
        cardSet.setCreator(creator);
        cardSet.setFirstLanguage(Language.valueOf(cardSetDTO.getFirstLanguage()));
        cardSet.setSecondLanguage(Language.valueOf(cardSetDTO.getSecondLanguage()));

        return cardSetRepository.save(cardSet);
    }



    public List<CardSetDTO> getAllCardSetsDTO() {
        List<CardSet> cardSets = cardSetRepository.findAll();
        return cardSets.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public CardSetDTO getCardSetById(Long id) {
        CardSet cardSet = cardSetRepository.findById(id)
                .orElseThrow(() -> new CardSetNotFoundException("Card Set not found"));
        return convertToDTO(cardSet);
    }


    public void deleteCardSet(Long id) {
        CardSet cardSet = cardSetRepository.findById(id)
                .orElseThrow(() -> new CardSetNotFoundException("Card Set not found"));

        cardSetRepository.delete(cardSet);
    }

    public CardSetDTO updateCardSet(Long id, CardSetDTO cardSetDTO) {
        return cardSetRepository.findById(id)
                .map(existingCardSet -> {
                    existingCardSet.setName(cardSetDTO.getName());
                    existingCardSet.setPublic(cardSetDTO.isPublic());
                    existingCardSet.setFirstLanguage(Language.valueOf(cardSetDTO.getFirstLanguage()));
                    existingCardSet.setSecondLanguage(Language.valueOf(cardSetDTO.getSecondLanguage()));
                    return convertToDTO(cardSetRepository.save(existingCardSet));
                })
                .orElseThrow(() -> new CardSetNotFoundException("Card Set not found"));
    }



    public CardSetDTO convertToDTO(CardSet cardSet) {
        return new CardSetDTO(
                cardSet.getId(),
                cardSet.getName(),
                cardSet.isPublic(),
                cardSet.getFirstLanguage().toString(),
                cardSet.getSecondLanguage().toString()
        );
    }
}
