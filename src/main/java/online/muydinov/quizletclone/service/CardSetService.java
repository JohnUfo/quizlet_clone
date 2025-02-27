package online.muydinov.quizletclone.service;

import lombok.RequiredArgsConstructor;
import online.muydinov.quizletclone.record.CardSetRecord;
import online.muydinov.quizletclone.record.UserRecord;
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

    public List<CardSetRecord> getAllCardSets() {
        String username = myUserDetailsService.getUsername();
        Long currentUserId = myUserDetailsService.getUserIdByUsername(username);

        return cardSetRepository.findAllPublicAndAccessibleCardsets(currentUserId);
    }

    public CardSetRecord getCardSetById(Long cardSetId) {
        String username = myUserDetailsService.getUsername();
        Long currentUserId = myUserDetailsService.getUserIdByUsername(username);

        return cardSetRepository.findPublicAndAccessibleCardsets(cardSetId, currentUserId);
    }

    public CardSetRecord createCardSet(CardSetRecord cardSetRecord) {
        UserRecord creatorRecord = userRepository.findUserRecordByUsername(myUserDetailsService.getUsername())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        User creator = new User();
        creator.setId(creatorRecord.id());

        CardSet cardSet = new CardSet();
        cardSet.setName(cardSetRecord.name());
        cardSet.setPublic(cardSetRecord.isPublic());
        cardSet.setCreator(creator);
        cardSet.setFirstLanguage(cardSetRecord.firstLanguage());
        cardSet.setSecondLanguage(cardSetRecord.secondLanguage());

        return convertCardSetToRecord(cardSetRepository.save(cardSet));
    }

    public void deleteCardSet(Long id) {
        CardSet cardSet = findCardSetByIdAndVerifyOwner(id);
        cardSetRepository.delete(cardSet);
    }

    public CardSetRecord updateCardSet(Long id, CardSetRecord cardSetRecord) {
        CardSet cardSet = findCardSetByIdAndVerifyOwner(id);

        cardSet.setName(cardSetRecord.name());
        cardSet.setPublic(cardSetRecord.isPublic());
        cardSet.setFirstLanguage(cardSetRecord.firstLanguage());
        cardSet.setSecondLanguage(cardSetRecord.secondLanguage());

        return convertCardSetToRecord(cardSetRepository.save(cardSet));
    }

    public CardSet findCardSetByIdAndVerifyOwner(Long id) {
        return cardSetRepository.findByIdAndOwner(id, myUserDetailsService.getUsername())
                .orElseThrow(() -> new UnauthorizedAccessException("Access denied or Card Set not found"));
    }

    private CardSetRecord convertCardSetToRecord(CardSet cardSet) {
        return new CardSetRecord(
                cardSet.getId(),
                cardSet.getName(),
                cardSet.isPublic(),
                cardSet.getFirstLanguage(),
                cardSet.getSecondLanguage(),
                cardSet.getCreator().getId(),
                null,
                null
        );
    }
}