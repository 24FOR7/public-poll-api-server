package twentyfourFORseven.publicpoll.dto;

import lombok.*;
import twentyfourFORseven.publicpoll.domain.entity.Ballot;
import twentyfourFORseven.publicpoll.domain.entity.Poll;
import twentyfourFORseven.publicpoll.domain.entity.User;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class AddBallotDto {
    private Integer id;
    private User user;
    private Integer pollId;
    private Set<Integer> itemNum;
    private LocalDateTime createdAt;

    public Ballot toEntity(Poll poll, Integer currNum) {
        return Ballot.builder()
                .id(id)
                .user(user)
                .poll(poll)
                .itemNum(currNum)
                .build();
    }

    @Builder
    public AddBallotDto(Integer pollId, Set<Integer> itemNum) {
        this.pollId = pollId;
        this.itemNum = itemNum;
    }
}