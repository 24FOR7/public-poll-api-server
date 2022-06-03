package twentyfourFORseven.publicpoll.service;

import twentyfourFORseven.publicpoll.domain.entity.HashTag;
import twentyfourFORseven.publicpoll.domain.entity.Item;
import twentyfourFORseven.publicpoll.domain.repository.*;
import twentyfourFORseven.publicpoll.domain.repository.Mapping.*;
import twentyfourFORseven.publicpoll.domain.entity.Poll;
import twentyfourFORseven.publicpoll.domain.entity.User;
import twentyfourFORseven.publicpoll.dto.*;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class PollService {
    private PollRepository pollRepository;
    private UserRepository userRepository;
    private ItemRepository itemRepository;
    private HashTagService hashTagService;
    private BallotRepository ballotRepository;
    private CommentRepository commentRepository;

    public PollService(PollRepository pollRepository, UserRepository userRepository, ItemRepository itemRepository, HashTagService hashTagService, BallotRepository ballotRepository, CommentRepository commentRepository) {
        this.pollRepository = pollRepository;
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
        this.hashTagService = hashTagService;
        this.ballotRepository = ballotRepository;
        this.commentRepository = commentRepository;
    }

    /**
     * 변경
     * 투표를 추가하고 해당 정보를 반환
     */
    @Transactional
    public Optional<PollMapping> addPoll(String uid, AddPollDto addPollDto) {
        Optional<User> user = userRepository.findByUid(uid);
        if (user.isPresent()) {
            addPollDto.setUser(user.get());

            System.out.println(addPollDto.getHashTags().toString());
            Set<HashTag> tags = hashTagService.makeListOfHashTag(addPollDto.getHashTags());
            Poll poll = pollRepository.save(addPollDto.toEntity(tags));

            // item 추가하기
//            Object[] itemsArr = addPollDto.getItems().toArray();
//            for (int i = 0;i<itemsArr.length;i++) {
//                AddItemDto currItemDto = (AddItemDto) itemsArr[i];
//                itemRepository.save(currItemDto.toEntity(poll));
//            }


            for (int i = 0; i < addPollDto.getItems().size(); i++) {
                AddItemDto currItemDto = addPollDto.getItems().get(i);
                itemRepository.save(currItemDto.toEntity(poll));
            }

            return pollRepository.findOneById(poll.getId());
        }
        return Optional.empty();
    }

    /**
     * 전체 투표 조회
     */
    public List<PollMapping> findPolls(){
        return pollRepository.findAllBy();
    }

    /**
     * 핫투표 조회
     */
    public List findHotPolls(){
        List<SqlPollMapping> hotPollIdList = pollRepository.findByEndTimeAndBallot(LocalDateTime.now());

        ArrayList<SqlPollMapping> temp = new ArrayList<SqlPollMapping>(hotPollIdList);
        temp.sort(Comparator.comparing(SqlPollMapping::getCnt));
        Collections.reverse(temp);

        List<HotPollMapping> pollList = new ArrayList<>();

        Map<String, List<HotPollMapping>> map = new HashMap<>();
        Map<String, List<SqlPollMapping>> map2 = new HashMap<>();
//        map.put("polls", temp);
        map2.put("count", hotPollIdList);
        temp.forEach(obj->{
            pollList.add(pollRepository.getOneById(obj.getPoll_id()));
        });
        map.put("polls", pollList);
//        idList.add(map2);
        List result = new ArrayList<>();
        result.add(map);
        result.add(map2);
        return result;
    }

    /**
     * 내 투표 반환
     */
    public List<PollMapping> findMyPolls(String uid){
        Optional<User> user = userRepository.findByUid(uid);
        return pollRepository.findAllByUser(user.get());
    }

    /**
     * 빠른투표 반환
     */
    public List<Optional> getSpeedPolls(String uid){
        List<Integer> ids = itemRepository.findAllBinaryPoll();
        List<Optional> speedPolls = new ArrayList<>();
        for (int i = 0; i < ids.size(); i++) {
            speedPolls.add(showPoll(uid, ids.get(i)));
        }
        // Collections.shuffle(speedPolls);
        return speedPolls;
    }


    /**
     * 투표 검색
     */
    public List<PollMapping> searchPolls(String keyWord){
        return pollRepository.findByContents(keyWord);
    }

    /**
     * 투표를 가져와서 내 밸롯 정보도 같이 반환
     */
    public Optional showPoll(String uid, int pollId) {
        Optional<Poll> poll = pollRepository.findById(pollId);
        List<SqlBallotMapping> myBallots = ballotRepository.findAllByPollAndUid(pollId, uid);

        //List<Item> items = itemRepository.findAllByPollId(pollId);
        List<SqlItemMapping> items = itemRepository.findAllItemByPollId(pollId);

        GetPollNotVotedDto pollDto = new GetPollNotVotedDto(poll.get());
        pollDto.setItems(items);

        if(myBallots.size() == 0) {
            return Optional.of(pollDto);
        } else {
            // 내 선택 추가하기
            pollDto.setMyBallots(new ArrayList<>());
            for (int i = 0; i < myBallots.size(); i++) {
                pollDto.getMyBallots().add(myBallots.get(i).getItem_num());
            }

            // 통계 추가하기
            pollDto.setStats(new ArrayList<>());
            int totalBallotCnt = ballotRepository.findAllByPollId(pollId).size();
            for(int i = 1; i <= items.size(); i++) {
                int itemBallotCnt = ballotRepository.findAllByPollIdAndItemNum(pollId, i).size();
                pollDto.getStats().add(new StatDto(i, totalBallotCnt, itemBallotCnt, (double)itemBallotCnt/(double)totalBallotCnt));
            }

            return Optional.of(pollDto);
        }
    }


   /**
     * 투표 삭제
     * 아이템 삭제 후 투표 삭제
     */
    @Transactional
    public int removePoll(String uid, int pollId){
        Optional<Poll> poll = pollRepository.findById(pollId);
        if(poll.isPresent()){   //투표가 존재하는지 확인
            String uidFromPoll = poll.get().getUser().getUid();
            if (uid.equals(uidFromPoll)){   //투표 작성자와 로그인한 사용자가 같은지 확인
                ballotRepository.deleteByPoll(poll.get());
                commentRepository.deleteByPoll(poll.get());
                itemRepository.deleteByPoll(poll.get());
                pollRepository.deleteById(pollId);
                return 0;   //삭제 성공
            }
            return 1;   //투표 작성자와 로그인한 사용자가 다름
        }
        return 2;  //투표가 없음
    }



}

