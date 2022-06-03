package twentyfourFORseven.publicpoll.domain.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import twentyfourFORseven.publicpoll.domain.entity.User;
import twentyfourFORseven.publicpoll.domain.repository.Mapping.PollMapping;
import twentyfourFORseven.publicpoll.domain.entity.Poll;
import org.springframework.data.jpa.repository.JpaRepository;
import twentyfourFORseven.publicpoll.domain.repository.Mapping.HotPollMapping;
import twentyfourFORseven.publicpoll.domain.repository.Mapping.SqlPollMapping;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PollRepository extends JpaRepository<Poll, Integer> {

//    findById가 설정이 안되어서 일단 이렇게 해둠...
//    Optional<PollMapping> findByIdAndContents(int id, String contents);
//    이거로 대체 가능
    Optional<PollMapping> findOneById(int id);

    Optional<Poll> findById(int id);

    HotPollMapping getOneById(int id);

    List<PollMapping> findAllBy();

    List<PollMapping> findAllByUser(User user);

    List<PollMapping> findByContents(String keyWord);

    @Query(value = "SELECT poll_id, COUNT(poll_id) AS cnt\n" +
            "FROM ballot AS b\n" +
            "LEFT JOIN poll AS p\n" +
            "ON b.poll_id = p.id\n" +
            "WHERE p.end_time >= NOW()\n" +
            "GROUP BY poll_id\n" +
            "ORDER BY cnt DESC\n" +
            "LIMIT 10;", nativeQuery = true)
    List<SqlPollMapping> findByEndTimeAndBallot(@Param("currentTime") LocalDateTime  currentTime);



}