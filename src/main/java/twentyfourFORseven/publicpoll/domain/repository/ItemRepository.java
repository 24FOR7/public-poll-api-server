package twentyfourFORseven.publicpoll.domain.repository;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import twentyfourFORseven.publicpoll.domain.entity.Item;
import twentyfourFORseven.publicpoll.domain.entity.Poll;
import org.springframework.data.jpa.repository.JpaRepository;
import twentyfourFORseven.publicpoll.domain.repository.Mapping.SqlItemMapping;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Integer> {
    List<Item> findAllByPollId(Integer id);
    void deleteByPoll(Poll id);

    @Query(value = "SELECT poll_id\n" +
            "FROM ( SELECT poll_id, COUNT(poll_id) AS cnt FROM item GROUP BY poll_id) AS a\n" +
            "JOIN poll ON a.poll_id = poll.id\n" +
            "WHERE a.cnt = 2 AND has_image = 0", nativeQuery = true)
    List<Integer> findAllBinaryPoll();

    @Query(value = "SELECT * FROM item WHERE poll_id = :id", nativeQuery = true)
    List<SqlItemMapping> findAllItemByPollId(@Param("id") Integer id);

}