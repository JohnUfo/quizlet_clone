package online.muydinov.quizletclone.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CardSetDTO {
    private Long id;
    private String name;
    private boolean isPublic;
    private String firstLanguage;
    private String secondLanguage;
}
