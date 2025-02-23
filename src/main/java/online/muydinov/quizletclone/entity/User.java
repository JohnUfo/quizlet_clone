package online.muydinov.quizletclone.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

import static jakarta.persistence.CascadeType.ALL;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "creator", cascade = ALL, orphanRemoval = true)
    @JsonIgnore
    private List<CardSet> createdSets;

    @ManyToMany(mappedBy = "approvedUsers")
    @JsonIgnore
    private Set<CardSet> accessibleSets;
}
