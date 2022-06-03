package twentyfourFORseven.publicpoll.domain.repository.Mapping;

import twentyfourFORseven.publicpoll.domain.entity.User;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * res반환 시 데이터 가공 인터페이스
 */
public interface PollMapping {
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
    void setPresentImagePath(String path);

    interface UserData{
        String getNick();
        Integer getTier();
    }
    interface HashTagData{
        int getId();
        String getName();
    }
}
