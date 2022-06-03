package twentyfourFORseven.publicpoll.dto;

import lombok.*;
import twentyfourFORseven.publicpoll.domain.entity.*;
import twentyfourFORseven.publicpoll.domain.repository.Mapping.SqlItemMapping;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Getter
@Setter
@ToString
@NoArgsConstructor
public class GetPollNotVotedDto {
    private Integer id;
    private String nick;
    private Integer tier;
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
    private List<SqlItemMapping> items;

    private List<Integer> myBallots;
    private List<StatDto> stats;

    @Builder
    public GetPollNotVotedDto(Poll poll){
        id = poll.getId();
        nick = poll.getUser().getNick();
        tier = poll.getUser().getTier();
        this.contents = poll.getContents();

        Set<HashTag> temp = new HashSet<>();
        poll.getHashTags().forEach(hashTag -> temp.add(new HashTag(hashTag.getId(), hashTag.getName())));
        this.hashTag = temp;

        this.endTime = poll.getEndTime();
        this.hasImage = poll.getHasImage();
        this.isPublic = poll.getIsPublic();
        this.showNick = poll.getShowNick();
        this.canRevote = poll.getCanRevote();
        this.canComment = poll.getCanComment();
        this.isSingleVote = poll.getIsSingleVote();
        this.createdAt = poll.getCreatedAt();

    }
}