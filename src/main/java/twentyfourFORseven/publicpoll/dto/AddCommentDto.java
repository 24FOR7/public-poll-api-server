package twentyfourFORseven.publicpoll.dto;

import lombok.*;
import twentyfourFORseven.publicpoll.domain.entity.Comment;
import twentyfourFORseven.publicpoll.domain.entity.Poll;
import twentyfourFORseven.publicpoll.domain.entity.User;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class AddCommentDto {
    private Integer id;
    private User user;
    private Integer pollId;
    private String contents;
    private boolean hasImage;

    public Comment toEntity(Poll poll) {
        return Comment.builder()
                .id(id)
                .user(user)
                .poll(poll)
                .contents(contents)
                .build();
    }

    @Builder
    public AddCommentDto(Integer pollId, String contents) {
        this.pollId = pollId;
        this.contents = contents;
    }
}