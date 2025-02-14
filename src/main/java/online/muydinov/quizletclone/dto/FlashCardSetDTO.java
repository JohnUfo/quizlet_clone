package online.muydinov.quizletclone.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlashCardSetDTO {
    private String name;
    private boolean isPublic;
}
