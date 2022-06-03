package twentyfourFORseven.publicpoll.domain.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class) /* JPA에게 해당 Entity는 Auditiong 기능을 사용함을 알립니다. */
public class HashTag {

    @Id
    @GeneratedValue
    private int id;

    @Column(length=20, nullable = false)
    private String name;

    @ManyToMany(mappedBy = "hashTags")
    @JsonManagedReference //순환참조 방지
    private List<Poll> polls;

    @Builder
    public HashTag(int id, String name){
        this.id = id;
        this.name = name;
    }
}
