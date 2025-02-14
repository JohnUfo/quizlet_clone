package online.muydinov.quizletclone.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import online.muydinov.quizletclone.entity.FlashcardSet;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlashCardSetDTO {
    private Long id;
    private String name;
    private boolean isPublic;



}
