package twentyfourFORseven.publicpoll.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import twentyfourFORseven.publicpoll.domain.entity.Comment;
import twentyfourFORseven.publicpoll.domain.entity.Poll;
import twentyfourFORseven.publicpoll.domain.repository.Mapping.CommentMapping;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    Optional<CommentMapping> findOneById(int id);
    List<CommentMapping> findAllByPollId(int pollId);
    void deleteByPoll(Poll id);

}
