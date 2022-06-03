package twentyfourFORseven.publicpoll.domain.repository.Mapping;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * res반환 시 데이터 가공 인터페이스
 */
public interface HotPollMapping {
    Integer getId();
    UserData getUser();
    Set<HashTagData> getHashTags();
    String getContents();
    LocalDateTime getEndTime();
    Boolean getHasImage();
    String getPresentImagePath();
    Boolean getIsPublic();
    Boolean getShowNick();
    Boolean getCanRevote();
    Boolean getCanComment();
    Boolean getIsSingleVote();
    LocalDateTime getCreatedAt();

    interface UserData{
        String getNick();
        Integer getTier();
    }
    interface HashTagData{
        int getId();
        String getName();
    }
}
