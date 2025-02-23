package online.muydinov.quizletclone.service;

import lombok.RequiredArgsConstructor;
import online.muydinov.quizletclone.dto.CardSetDTO;
import online.muydinov.quizletclone.dto.UserDTO;
import online.muydinov.quizletclone.entity.CardSet;
import online.muydinov.quizletclone.entity.User;
import online.muydinov.quizletclone.exceptions.UnauthorizedAccessException;
import online.muydinov.quizletclone.exceptions.UserNotFoundException;
import online.muydinov.quizletclone.repository.CardSetRepository;
import online.muydinov.quizletclone.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CardSetService {

    private final CardSetRepository cardSetRepository;
    private final MyUserDetailsService myUserDetailsService;
    private final UserRepository userRepository;

    public List<CardSetDTO> getAllCardSets() {
        String username = myUserDetailsService.getUsername();
        Long currentUserId = myUserDetailsService.getUserIdByUsername(username);

        return cardSetRepository.findAllPublicAndAccessibleCardsets(currentUserId);
    }

    public CardSetDTO getCardSetById(Long cardSetId) {
        String username = myUserDetailsService.getUsername();
        Long currentUserId = myUserDetailsService.getUserIdByUsername(username);

        return cardSetRepository.findPublicAndAccessibleCardsets(cardSetId,currentUserId);
    }

    public CardSetDTO createCardSet(CardSetDTO cardSetDTO) {
        UserDTO creatorDTO = userRepository.findUserDTOByUsername(myUserDetailsService.getUsername())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        User creator = new User();
        creator.setId(creatorDTO.getId());

        CardSet cardSet = new CardSet();
        cardSet.setName(cardSetDTO.getName());
        cardSet.setPublic(cardSetDTO.isPublic());
        cardSet.setCreator(creator);
        cardSet.setFirstLanguage(cardSetDTO.getFirstLanguage());
        cardSet.setSecondLanguage(cardSetDTO.getSecondLanguage());

        return convertCardSetToDTO(cardSetRepository.save(cardSet),"NO");
    }

    public void deleteCardSet(Long id) {
        CardSet cardSet = findCardSetByIdAndVerifyOwner(id);
        cardSetRepository.delete(cardSet);
    }

    public CardSetDTO updateCardSet(Long id, CardSetDTO cardSetDTO) {
        CardSet cardSet = findCardSetByIdAndVerifyOwner(id);

        cardSet.setName(cardSetDTO.getName());
        cardSet.setPublic(cardSetDTO.isPublic());
        cardSet.setFirstLanguage(cardSetDTO.getFirstLanguage());
        cardSet.setSecondLanguage(cardSetDTO.getSecondLanguage());

        return convertCardSetToDTO(cardSetRepository.save(cardSet),"NO");
    }

    private CardSet findCardSetByIdAndVerifyOwner(Long id) {
        return cardSetRepository.findByIdAndOwner(id, myUserDetailsService.getUsername())
                .orElseThrow(() -> new UnauthorizedAccessException("Access denied or Card Set not found"));
    }

    private CardSetDTO convertCardSetToDTO(CardSet cardSet,String accessible) {
        return new CardSetDTO(
                cardSet.getId(),
                cardSet.getName(),
                cardSet.isPublic(),
                cardSet.getFirstLanguage().toString(),
                cardSet.getSecondLanguage().toString(),
                cardSet.getCreator().getId(),
                accessible
        );
    }
}
