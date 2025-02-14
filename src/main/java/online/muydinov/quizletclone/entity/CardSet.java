package online.muydinov.quizletclone.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import online.muydinov.quizletclone.enums.Language;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CardSet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "cardSet", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Card> cards;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id", nullable = false)
    private User creator;

    @ManyToMany
    @JoinTable(
            name = "set_access_requests",
            joinColumns = @JoinColumn(name = "set_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> approvedUsers;

    private boolean isPublic;

    @Enumerated(EnumType.STRING)
    private Language firstLanguage;

    @Enumerated(EnumType.STRING)
    private Language secondLanguage;
}
