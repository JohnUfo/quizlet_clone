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
    private Long creatorId;
    private String creatorName;
    private String accessType;

    public CardSetDTO(Long id, String name, boolean isPublic, String firstLanguage, String secondLanguage, Long creatorId) {
        this.id = id;
        this.name = name;
        this.isPublic = isPublic;
        this.firstLanguage = firstLanguage;
        this.secondLanguage = secondLanguage;
        this.creatorId = creatorId;
    }

    public CardSetDTO(Long id, String name, boolean isPublic, String firstLanguage, String secondLanguage, Long creatorId, String accessType) {
        this.id = id;
        this.name = name;
        this.isPublic = isPublic;
        this.firstLanguage = firstLanguage;
        this.secondLanguage = secondLanguage;
        this.creatorId = creatorId;
        this.accessType = accessType;
    }
}