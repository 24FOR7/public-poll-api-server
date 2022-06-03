package twentyfourFORseven.publicpoll.service;

import org.springframework.stereotype.Service;
import twentyfourFORseven.publicpoll.domain.entity.Ballot;
import twentyfourFORseven.publicpoll.domain.entity.Item;
import twentyfourFORseven.publicpoll.domain.entity.Poll;
import twentyfourFORseven.publicpoll.domain.entity.User;

import twentyfourFORseven.publicpoll.domain.repository.BallotRepository;
import twentyfourFORseven.publicpoll.domain.repository.ItemRepository;
import twentyfourFORseven.publicpoll.domain.repository.Mapping.BallotMapping;
import twentyfourFORseven.publicpoll.domain.repository.Mapping.MyVotedPollMapping;
import twentyfourFORseven.publicpoll.domain.repository.PollRepository;
import twentyfourFORseven.publicpoll.domain.repository.UserRepository;
import twentyfourFORseven.publicpoll.dto.AddBallotDto;
import twentyfourFORseven.publicpoll.dto.StatDto;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class BallotService {
    private PollRepository pollRepository;
    private UserRepository userRepository;
    private BallotRepository ballotRepository;
    private ItemRepository itemRepository;

    public BallotService(PollRepository pollRepository, UserRepository userRepository, BallotRepository ballotRepository, ItemRepository itemRepository) {
        this.pollRepository = pollRepository;
        this.userRepository = userRepository;
        this.ballotRepository = ballotRepository;
        this.itemRepository = itemRepository;
    }

    /**
     * 밸롯 추가
     */
    @Transactional
    public List<StatDto> addBallot(String uid, AddBallotDto addBallotDto) {
        Optional<User> user = userRepository.findByUid(uid);
        if (user.isPresent()) {
            addBallotDto.setUser(user.get());

            Optional<Poll> poll = pollRepository.findById(addBallotDto.getPollId());

            Set<Optional<BallotMapping>> ballots = new HashSet<>();
            Ballot ballot = null;

            for (Integer itemNum : addBallotDto.getItemNum()) {
                ballot = ballotRepository.save(addBallotDto.toEntity(poll.get(), itemNum));
                ballots.add(ballotRepository.findOneById(ballot.getId()));
            }

            // 통계 추가하기
            List<Item> items = itemRepository.findAllByPollId(poll.get().getId());
            List<StatDto> stats = new ArrayList<>();
            int totalBallotCnt = ballotRepository.findAllByPollId(poll.get().getId()).size();
            int maxIndex = -1;
            int maxVal = -1;
            for(int i = 1; i <= items.size(); i++) {
                int itemBallotCnt = ballotRepository.findAllByPollIdAndItemNum(poll.get().getId(), i).size();
                stats.add(new StatDto(i, totalBallotCnt, itemBallotCnt, (double)itemBallotCnt/(double)totalBallotCnt));
                if (itemBallotCnt > maxVal) {
                    maxIndex = i-1;
                    maxVal = itemBallotCnt;
                }
            }
            stats.get(maxIndex).setIsBest(true);

            // 생성 완료 확인을 위해 마지막 밸롯 반환
            return stats;
        }
        return new ArrayList<>();
    }

    /**
     * 밸롯 재추가 (재투표)
     */
    @Transactional
    public List<StatDto> revoteBallot(String uid, AddBallotDto addBallotDto) {
        Optional<User> user = userRepository.findByUid(uid);
        if (user.isPresent()) {
            addBallotDto.setUser(user.get());

            Optional<Poll> poll = pollRepository.findById(addBallotDto.getPollId());

            // 기존 밸롯 삭제하기
            //System.out.println("delete pollid : " + poll.get().getId().toString() + "   uid : " + addBallotDto.getUser().getUid().toString());
            ballotRepository.deleteByPollAndUid(poll.get().getId(), addBallotDto.getUser().getUid());

            Set<Optional<BallotMapping>> ballots = new HashSet<>();
            Ballot ballot = null;

            for (Integer itemNum : addBallotDto.getItemNum()) {
                //System.out.println("new ballot pollid : " + addBallotDto.getPollId() + "  itemnum : " + addBallotDto.getItemNum() + " uid : " + addBallotDto.getUser().getUid().toString());
                ballot = ballotRepository.save(addBallotDto.toEntity(poll.get(), itemNum));
                ballots.add(ballotRepository.findOneById(ballot.getId()));
            }

            // 통계 추가하기
            List<Item> items = itemRepository.findAllByPollId(poll.get().getId());
            List<StatDto> stats = new ArrayList<>();
            int totalBallotCnt = ballotRepository.findAllByPollId(poll.get().getId()).size();
            int maxIndex = -1;
            int maxVal = -1;
            for(int i = 1; i <= items.size(); i++) {
                int itemBallotCnt = ballotRepository.findAllByPollIdAndItemNum(poll.get().getId(), i).size();
                stats.add(new StatDto(i, totalBallotCnt, itemBallotCnt, (double)itemBallotCnt/(double)totalBallotCnt));

                if (itemBallotCnt > maxVal) {
                    maxIndex = i-1;
                    maxVal = itemBallotCnt;
                }
            }


            stats.get(maxIndex).setIsBest(true);
            return stats;
        }
        return new ArrayList<>();
    }

    /**
     * 게시글별 밸롯 조회
     */
    public List<BallotMapping> getBallotByPollId(int pollId){
        return ballotRepository.findAllByPollId(pollId);
    }


    /**
     * 내 투표지 -> 투표 가져오기
     */
    public List<MyVotedPollMapping> findVotedPollId(String uid){
        Optional<User> user = userRepository.findByUid(uid);
        List<MyVotedPollMapping> votedPolls = ballotRepository.findAllByUser(user.get());

        List<MyVotedPollMapping> result = new ArrayList<>();
        if(votedPolls.size()==0)
            return result;

        result.add(votedPolls.get(0));
        for(int i=1; i<votedPolls.size(); i++){
                if(!Objects.equals(votedPolls.get(i - 1).getPoll().getId(), votedPolls.get(i).getPoll().getId())){
                    result.add(votedPolls.get(i));
                }
        }
        return result;
    }
}
