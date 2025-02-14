package online.muydinov.quizletclone.service;

import lombok.RequiredArgsConstructor;
import online.muydinov.quizletclone.dto.CardDTO;
import online.muydinov.quizletclone.entity.Card;
import online.muydinov.quizletclone.entity.CardSet;
import online.muydinov.quizletclone.entity.User;
import online.muydinov.quizletclone.exceptions.CardSetAlreadyExistsException;
import online.muydinov.quizletclone.exceptions.CardSetNotFoundException;
import online.muydinov.quizletclone.repository.CardRepository;
import online.muydinov.quizletclone.repository.CardSetRepository;
import org.springframework.stereotype.Service;

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
        cardRepository.save(new Card(null,cardDTO.getFirstCard(),cardDTO.getSecondCard(),cardSetRef));
    }
}
