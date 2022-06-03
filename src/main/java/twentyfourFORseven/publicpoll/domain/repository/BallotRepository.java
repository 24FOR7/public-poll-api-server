package twentyfourFORseven.publicpoll.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import twentyfourFORseven.publicpoll.domain.entity.Ballot;
import twentyfourFORseven.publicpoll.domain.entity.Poll;
import twentyfourFORseven.publicpoll.domain.entity.User;
import twentyfourFORseven.publicpoll.domain.repository.Mapping.BallotMapping;
import twentyfourFORseven.publicpoll.domain.repository.Mapping.MyVotedPollMapping;
import twentyfourFORseven.publicpoll.domain.repository.Mapping.SqlBallotMapping;
import twentyfourFORseven.publicpoll.domain.repository.Mapping.SqlBallotStatMapping;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface BallotRepository extends JpaRepository<Ballot, Integer> {
    Optional<BallotMapping> findOneById(int id);
    List<BallotMapping> findAllByPollId(int pollId);

    List<MyVotedPollMapping> findAllByUser(User user);

    /**
     * 투표, 유저로 검색하여 해당 투표에 대한 내 투표지 get
     */
    @Query(value = "SELECT id, poll_id, uid, item_num FROM ballot WHERE poll_id = :pollId AND uid = :uid", nativeQuery = true)
    List<SqlBallotMapping> findAllByPollAndUid(@Param("pollId") Integer pollId, @Param("uid") String uid);


    void deleteByPoll(Poll id);

    @Query(value = "DELETE FROM ballot WHERE poll_id = :pollId AND uid = :uid", nativeQuery = true)
    void deleteByPollAndUid(@Param("pollId") Integer pollId, @Param("uid") String uid);

    String originQuery = "SELECT b.poll_id, u.uid, u.age, u.gender, u.tier FROM ballot b JOIN user u ON u.uid = b.uid WHERE b.poll_id = :pollId AND b.item_num = :itemNum";

    @Query(value = originQuery, nativeQuery = true)
    List<SqlBallotStatMapping> findAllByPollIdAndItemNum(@Param("pollId") Integer pollId, @Param("itemNum") Integer itemNum);

    @Query(value = originQuery + " AND FLOOR(u.age/10) = :ageOption", nativeQuery = true)
    List<SqlBallotStatMapping> findAllQueryByAge(@Param("pollId") Integer pollId, @Param("itemNum") Integer itemNum, @Param("ageOption") Integer ageOption);

    @Query(value = originQuery + " AND u.gender = :gender", nativeQuery = true)
    List<SqlBallotStatMapping> findAllQueryByGender(@Param("pollId") Integer pollId, @Param("itemNum") Integer itemNum, @Param("gender") Character gender);

    @Query(value = originQuery + " AND u.tier = :tierOption", nativeQuery = true)
    List<SqlBallotStatMapping> findAllQueryByTier(@Param("pollId") Integer pollId, @Param("itemNum") Integer itemNum, @Param("tierOption") Integer tierOption);

    @Query(value = originQuery + " AND FLOOR(u.age/10) = :ageOption AND u.gender = :gender", nativeQuery = true)
    List<SqlBallotStatMapping> findAllQueryByAgeAndGender(@Param("pollId") Integer pollId, @Param("itemNum") Integer itemNum, @Param("ageOption") Integer ageOption, @Param("gender") Character gender);

    @Query(value = originQuery + " AND u.gender = :gender AND u.tier = :tierOption", nativeQuery = true)
    List<SqlBallotStatMapping> findAllQueryByGenderAndTier(@Param("pollId") Integer pollId, @Param("itemNum") Integer itemNum, @Param("gender") Character gender, @Param("tierOption") Integer tierOption);

    @Query(value = originQuery + " AND u.tier = :tierOption AND FLOOR(u.age/10) = :ageOption", nativeQuery = true)
    List<SqlBallotStatMapping> findAllQueryByTierAndAge(@Param("pollId") Integer pollId, @Param("itemNum") Integer itemNum, @Param("tierOption") Integer tierOption, @Param("ageOption") Integer ageOption);

    @Query(value = originQuery + " AND FLOOR(u.age/10) = :ageOption AND u.gender = :gender AND u.tier = :tierOption", nativeQuery = true)
    List<SqlBallotStatMapping> findAllQueryByAgeAndGenderAndTier(@Param("pollId") Integer pollId, @Param("itemNum") Integer itemNum, @Param("ageOption") Integer ageOption, @Param("gender") Character gender, @Param("tierOption") Integer tierOption);



}

