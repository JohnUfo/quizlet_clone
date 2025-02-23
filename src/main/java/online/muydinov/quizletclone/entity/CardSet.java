package online.muydinov.quizletclone.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class CardSet {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "cardSet", cascade = ALL, orphanRemoval = true, fetch = LAZY)
    @JsonManagedReference
    private List<Card> cards;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "creator_id", nullable = false)
    private User creator;

    @ManyToMany(fetch = LAZY, cascade = {PERSIST, MERGE, REMOVE})
    @JoinTable(
            name = "set_access_requests",
            joinColumns = @JoinColumn(name = "set_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> approvedUsers;

    private boolean isPublic;

    @Column(nullable = false)
    private String firstLanguage;
    @Column(nullable = false)
    private String secondLanguage;
}
