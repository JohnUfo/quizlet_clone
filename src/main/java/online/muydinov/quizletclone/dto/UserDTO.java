package online.muydinov.quizletclone.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String fullName;
    private String username;
    private List<CardSetDTO> createdSets;
    private Set<CardSetDTO> accessibleSets;
}
