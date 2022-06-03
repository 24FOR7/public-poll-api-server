package twentyfourFORseven.publicpoll.domain.entity;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Getter
@Setter
@Entity(name="item")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Item {
    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne(targetEntity = Poll.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "poll_id")
    private Poll poll;

    @Column(nullable = false)
    private Integer itemNum;

    @Column(length = 20, nullable = false)
    private String contents;

    @Column(nullable = false)
    private Boolean hasImage;

    @Builder
    public Item(Integer id, Poll poll, Integer itemNum, String contents, Boolean hasImage) {
        this.id = id;
        this.poll = poll;
        this.itemNum = itemNum;
        this.contents = contents;
        this.hasImage = hasImage;
    }
}
