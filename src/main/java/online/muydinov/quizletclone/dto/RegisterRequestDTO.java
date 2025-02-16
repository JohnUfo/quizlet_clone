package online.muydinov.quizletclone.dto;

import lombok.Data;

@Data
public class RegisterRequestDTO {
    private String fullName;
    private String username;
    private String password;
}
