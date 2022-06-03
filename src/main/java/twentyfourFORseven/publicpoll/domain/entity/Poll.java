package twentyfourFORseven.publicpoll.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class) /* JPA에게 해당 Entity는 Auditiong 기능을 사용함을 알립니다. */
public class Poll {

    @Id
    @GeneratedValue
    private Integer id;

//    @Column(length = 20, nullable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "uid")
    private User user;

    @ManyToMany //양방향 관계의 주인
    @JsonBackReference  //순환참조 방지
    @JoinTable(name = "poll_hashtag",
            joinColumns = @JoinColumn(name="poll_id"),
            inverseJoinColumns = @JoinColumn(name = "hashtag_id"))
    private Set<HashTag> hashTags = new HashSet<>();

    @Column(length = 20, nullable = false)
    private String contents;

    @Column(nullable = false)
    private LocalDateTime endTime;

    @Column(nullable = false)
    private Boolean hasImage;

    @Column(nullable = true)
    private String presentImagePath;

    @Column(nullable = false)
    private Boolean isPublic;

    @Column(nullable = false)
    private Boolean showNick;

    @Column(nullable = false)
    private Boolean canRevote;

    @Column(nullable = false)
    private Boolean canComment;

    @Column(nullable = false)
    private Boolean isSingleVote;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @Builder
    public Poll(Integer id, User user, Set<HashTag> hashTags, String contents, LocalDateTime endTime, Boolean hasImage, Boolean isPublic, Boolean showNick, Boolean canRevote, Boolean canComment, Boolean isSingleVote) {
        this.id = id;
        this.user = user;
        this.hashTags = hashTags;   //해시태그 리스트 생성 해줘야하나?
        this.contents = contents;
        this.endTime = endTime;
        this.hasImage = hasImage;
        this.isPublic = isPublic;
        this.showNick = showNick;
        this.canRevote = canRevote;
        this.canComment = canComment;
        this.isSingleVote = isSingleVote;
    }
}
