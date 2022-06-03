package twentyfourFORseven.publicpoll.dto;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import twentyfourFORseven.publicpoll.domain.entity.User;

import javax.persistence.Column;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserDto {
    private String uid;
    private String email;
    private String nick;
    private int age;
    private String gender;
    private int tier;
    private int userInterest1;
    private int userInterest2;
    private int userInterest3;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public User toEntity(){
        return User.builder()
                .uid(uid)
                .email(email)
                .nick(nick)
                .age(age)
                .gender(gender)
                .tier(tier)
                .userInterest1(userInterest1)
                .userInterest2(userInterest2)
                .userInterest3(userInterest3)
                .build();
    }

    //Dto생성 -> uid는 따로 얻어오므로 인스턴스 생성 후 setter로 추가 예정
    @Builder
    public UserDto(String email, String nick, int age, String gender,
                int tier, int userInterest1, int userInterest2, int userInterest3,
                   LocalDateTime createdAt, LocalDateTime updatedAt){
        this.email = email;
        this.nick = nick;
        this.age = age;
        this.gender = gender;
        this.tier = tier;
        this.userInterest1 = userInterest1;
        this.userInterest2 = userInterest2;
        this.userInterest3 = userInterest3;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
