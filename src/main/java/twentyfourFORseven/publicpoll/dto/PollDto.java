package twentyfourFORseven.publicpoll.dto;

import twentyfourFORseven.publicpoll.domain.entity.HashTag;
import twentyfourFORseven.publicpoll.domain.entity.Poll;
import lombok.*;
import twentyfourFORseven.publicpoll.domain.entity.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class PollDto {
    private Integer id;
    private User user;
    private String contents;
    private Set<HashTag> hashTag;
    private LocalDateTime endTime;
    private Boolean hasImage;
    private Boolean isPublic;
    private Boolean showNick;
    private Boolean canRevote;
    private Boolean canComment;
    private Boolean isSingleVote;
    private LocalDateTime createdAt;

    public Poll toEntity() {
        return Poll.builder()
                .id(id)
                .user(user)
                .contents(contents)
                .endTime(endTime)
                .hasImage(hasImage)
                .isPublic(isPublic)
                .showNick(showNick)
                .canRevote(canRevote)
                .canComment(canComment)
                .isPublic(isPublic)
                .isSingleVote(isSingleVote)
                .build();
    }

    @Builder
    public PollDto(/*Integer id, User user,*/ String contents, Set<HashTag> hashTags, LocalDateTime endTime, Boolean hasImage, Boolean isPublic, Boolean showNick, Boolean canRevote, Boolean canComment, Boolean isSingleVote, LocalDateTime createdAt) {
//        this.id = id;
//        this.user = user;
        this.contents = contents;
        this.hashTag = hashTags;    //해시태그 dto를 poll dto안에 넣어야 request에서 자동 생성해서 받아올 수 있을것같
        this.endTime = endTime;
        this.hasImage = hasImage;
        this.isPublic = isPublic;
        this.showNick = showNick;
        this.canRevote = canRevote;
        this.canComment = canComment;
        this.isSingleVote = isSingleVote;
        this.createdAt = createdAt;
    }
}
