package twentyfourFORseven.publicpoll.service;

import org.springframework.stereotype.Service;
import twentyfourFORseven.publicpoll.domain.entity.Comment;
import twentyfourFORseven.publicpoll.domain.entity.Poll;
import twentyfourFORseven.publicpoll.domain.entity.User;
import twentyfourFORseven.publicpoll.domain.repository.CommentRepository;
import twentyfourFORseven.publicpoll.domain.repository.Mapping.CommentMapping;
import twentyfourFORseven.publicpoll.domain.repository.Mapping.PollMapping;
import twentyfourFORseven.publicpoll.domain.repository.PollRepository;
import twentyfourFORseven.publicpoll.domain.repository.UserRepository;
import twentyfourFORseven.publicpoll.dto.AddCommentDto;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    private PollRepository pollRepository;
    private UserRepository userRepository;
    private CommentRepository commentRepository;

    public CommentService(PollRepository pollRepository, UserRepository userRepository, CommentRepository commentRepository) {
        this.pollRepository = pollRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
    }

    /**
     * 댓글 추가
     */
    @Transactional
    public Optional<CommentMapping> addComment(String uid, AddCommentDto addCommentDto) {
        Optional<User> user = userRepository.findByUid(uid);
        if (user.isPresent()) {
            addCommentDto.setUser(user.get());

            Optional<Poll> poll = pollRepository.findById(addCommentDto.getPollId());
            Comment comment = commentRepository.save(addCommentDto.toEntity(poll.get()));

            return commentRepository.findOneById(comment.getId());
        }
        return Optional.empty();
    }

    /**
    * 댓글 조회
    */
    public List<CommentMapping> getCommentByPollId(int pollId){
        return commentRepository.findAllByPollId(pollId);
    }
}
