package online.muydinov.quizletclone.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import online.muydinov.quizletclone.enums.RequestStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SetAccessRequestDTO {
    private Long id;
    private Long cardSetId;
    private String cardSetName;
    private String requesterUsername;
    private RequestStatus status;
}
