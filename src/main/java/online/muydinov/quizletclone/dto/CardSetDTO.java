package online.muydinov.quizletclone.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    private Boolean isPublic;
    private String firstLanguage;
    private String secondLanguage;
    private Long ownerId;
}
