package online.muydinov.quizletclone.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardSetDTO {
    private Long id;
    private String name;
    private boolean isPublic;
}
