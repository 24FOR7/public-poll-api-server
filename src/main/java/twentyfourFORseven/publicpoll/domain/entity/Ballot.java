package twentyfourFORseven.publicpoll.domain.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Ballot {
    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "uid")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "poll_id")
    private Poll poll;

    @Column(nullable = false)
    private Integer itemNum;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;


    @Builder
    public Ballot(Integer id, User user, Poll poll, Integer itemNum) {
        this.id = id;
        this.poll = poll;
        this.user = user;
        this.itemNum = itemNum;
    }
}