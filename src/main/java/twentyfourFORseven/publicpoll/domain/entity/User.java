package twentyfourFORseven.publicpoll.domain.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor(access= AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class User {

    @Id //pk설정
    @JsonIgnore
    private String uid;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String nick;

    @Column(nullable = false)
    private int age;

    @Column(nullable = false)
    private String gender;

    @Column(nullable = false)
    private int tier;

    @Column(nullable = false)
    private int userInterest1;

    @Column(nullable = false)
    private int userInterest2;

    @Column(nullable = false)
    private int userInterest3;

    @CreatedDate
    @Column(updatable = false)
    @JsonIgnore
    private LocalDateTime createdAt;

    @LastModifiedDate   //마지막 수정된 시간 = updatedAt
    @Column
    @JsonIgnore
    private LocalDateTime updatedAt;

    @Builder
    public User(String uid, String email, String nick, int age, String gender,
                int tier, int userInterest1, int userInterest2, int userInterest3){
        this.uid = uid;
        this.email = email;
        this.nick = nick;
        this.age = age;
        this.gender = gender;
        this.tier = tier;
        this.userInterest1 = userInterest1;
        this.userInterest2 = userInterest2;
        this.userInterest3 = userInterest3;
    }

}
