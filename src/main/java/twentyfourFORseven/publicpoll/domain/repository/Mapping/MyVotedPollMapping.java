package twentyfourFORseven.publicpoll.domain.repository.Mapping;

import java.time.LocalDateTime;
import java.util.Set;

public interface MyVotedPollMapping {
//    Integer getId();
    PollData getPoll();
//    Integer getItemNum();


    interface PollData{
        Integer getId();
        Set<PollMapping.HashTagData> getHashTags();
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
}

