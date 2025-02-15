package online.muydinov.quizletclone.dto;

import lombok.Data;
import online.muydinov.quizletclone.enums.RequestStatus;

@Data
public class SetAccessRequestDTO {
    private Long requestId;
    private String requesterUsername;
    private RequestStatus status;

    public SetAccessRequestDTO(Long requestId, String requesterUsername, RequestStatus status) {
        this.requestId = requestId;
        this.requesterUsername = requesterUsername;
        this.status = status;
    }
}
