package online.muydinov.quizletclone.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CardSetWithCardsDTO {
    private Long id;
    private String name;
    private boolean isPublic;
    private String firstLanguage;
    private String secondLanguage;
    private Long ownerId;
    private List<CardDTO> cards;
}
