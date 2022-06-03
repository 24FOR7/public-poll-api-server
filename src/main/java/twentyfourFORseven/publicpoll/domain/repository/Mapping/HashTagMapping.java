package twentyfourFORseven.publicpoll.domain.repository.Mapping;

import java.time.LocalDateTime;
import java.util.List;

public interface HashTagMapping {
    int getId();
    String getName();
    List<PollData> getPolls();

    interface PollData{
        int getId();
        LocalDateTime getCreatedAt();
        LocalDateTime getEndTime();
        String getContents();
        String getPresentImagePath();

        UserData getUser();

        interface UserData{
            String getNick();
            int getTier();
        }
    }
}
