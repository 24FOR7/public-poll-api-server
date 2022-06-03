package twentyfourFORseven.publicpoll.dto;

import lombok.*;
import twentyfourFORseven.publicpoll.domain.entity.Item;
import twentyfourFORseven.publicpoll.domain.repository.Mapping.PollMapping;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class GetSpeedPollDto {
    private Integer id;
    private PollMapping.UserData user;
    private String contents;
    private Set<PollMapping.HashTagData> hashTag;
    private LocalDateTime endTime;
    private Boolean hasImage;
    private Boolean isPublic;
    private Boolean showNick;
    private Boolean canRevote;
    private Boolean canComment;
    private Boolean isSingleVote;
    private LocalDateTime createdAt;
    private List<Item> items;

    @Builder
    public GetSpeedPollDto (PollMapping pollMapping) {
        this.id = pollMapping.getId();
        this.user = pollMapping.getUser();
        this.contents = pollMapping.getContents();
        this.hashTag = pollMapping.getHashTags();  //해시태그 dto를 poll dto안에 넣어야 request에서 자동 생성해서 받아올 수 있을것같
        this.endTime = pollMapping.getEndTime();
        this.hasImage = pollMapping.getHasImage();
        this.isPublic = pollMapping.getIsPublic();
        this.showNick = pollMapping.getShowNick();
        this.canRevote = pollMapping.getCanRevote();
        this.canComment = pollMapping.getCanComment();
        this.isSingleVote = pollMapping.getIsSingleVote();
        this.createdAt = pollMapping.getCreatedAt();
    }
}
