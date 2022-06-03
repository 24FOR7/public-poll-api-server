package twentyfourFORseven.publicpoll.domain.entity;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Getter
@Setter
@Entity(name="Comment")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Comment {
    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "uid")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "poll_id")
    private Poll poll;

    @Column(length = 20, nullable = false)
    private String contents;


    @Builder
    public Comment(Integer id, User user, Poll poll, String contents) {
        this.id = id;
        this.poll = poll;
        this.user = user;
        this.contents = contents;
    }
}