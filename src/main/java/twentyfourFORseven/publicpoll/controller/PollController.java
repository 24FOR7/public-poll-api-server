package twentyfourFORseven.publicpoll.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import twentyfourFORseven.publicpoll.RestApiResponse.DefaultRes;
import twentyfourFORseven.publicpoll.RestApiResponse.StatusCode;
import twentyfourFORseven.publicpoll.domain.entity.Poll;
import twentyfourFORseven.publicpoll.domain.repository.Mapping.PollMapping;
import twentyfourFORseven.publicpoll.domain.repository.Mapping.HotPollMapping;
import twentyfourFORseven.publicpoll.dto.AddPollDto;
import twentyfourFORseven.publicpoll.dto.GetSpeedPollDto;
import twentyfourFORseven.publicpoll.service.PollService;
import twentyfourFORseven.publicpoll.service.UserService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/poll")
public class PollController {
    private PollService pollService;
    private UserService userService;

    public PollController(PollService pollService, UserService userService) {
        this.pollService = pollService;
        this.userService = userService;
    }

    /**
     * 투표 생성
     */
    @PostMapping("/add")
    public ResponseEntity createPoll(@RequestAttribute String uid, @RequestBody AddPollDto addPollDto){
        // String uid = "Wa7vHCo5wkND42B2iNWduigDKSy2";
        //List<String> hashTags = new ArrayList<>();  //클라이언트가 보낸 request body에서 해시태그 리스트 추출한것으로 변경(임시방편으로 어레이 만들어둠)
        Optional<PollMapping> newPoll = pollService.addPoll(uid, addPollDto);

        if (newPoll.isPresent()){
            String resMessage = "투표 생성 완료";
            userService.updateTier(uid);
            return new ResponseEntity(DefaultRes.res(StatusCode.OK, resMessage, newPoll.get()), HttpStatus.OK);
        }
        String resMessage = "투표 생성 실패";
        return new ResponseEntity(DefaultRes.res(StatusCode.BAD_REQUEST, resMessage), HttpStatus.OK);
    }

    /**
     * 투표 반환
     */
    @GetMapping("/{pollId}")
    public ResponseEntity getPoll( @RequestAttribute String uid, @PathVariable("pollId") int pollId){
        //        @RequestAttribute String uid,
        //String uid = "Wa7vHCo5wkND42B2iNWduigDKSy2";
        Optional poll = pollService.showPoll(uid, pollId);
        return new ResponseEntity(DefaultRes.res(StatusCode.OK, "반환 성공", poll.get()), HttpStatus.OK);
    }

    /**
     * 전체 투표 반환
     */
    @GetMapping("/all")
    public ResponseEntity getPollList(){
        List<PollMapping> polls = pollService.findPolls();
        if(polls.size() == 0) {
            return new ResponseEntity(DefaultRes.res(StatusCode.OK, "진행중인 투표 없음", polls), HttpStatus.OK);
        }
        return new ResponseEntity(DefaultRes.res(StatusCode.OK, "전체 투표 반환 성공", polls), HttpStatus.OK);
    }

    @GetMapping("/my")
    public ResponseEntity getMyPollList(@RequestAttribute String uid){
        //        @RequestAttribute String uid,
        //String uid = "Wa7vHCo5wkND42B2iNWduigDKSy2";

        List<PollMapping> myPolls = pollService.findMyPolls(uid);
        if(myPolls.size() == 0) {
            return new ResponseEntity(DefaultRes.res(StatusCode.OK, "내 투표 없음", myPolls), HttpStatus.OK);
        }
        return new ResponseEntity(DefaultRes.res(StatusCode.OK, "내 투표 반환 성공", myPolls), HttpStatus.OK);

    }

    /**
     * 핫투표 반환
     */
    @GetMapping("hot")
    public ResponseEntity getHotPollList(){
        List<HotPollMapping> hotPolls = pollService.findHotPolls();
        if (hotPolls.size() == 0) {
            return new ResponseEntity(DefaultRes.res(StatusCode.OK, "인기투표 없음", hotPolls), HttpStatus.OK);
        }
        return new ResponseEntity(DefaultRes.res(StatusCode.OK, "인기투표 반환 성공", hotPolls), HttpStatus.OK);
    }

    /**
     * 빠른투표 반환
     */
    @GetMapping("/speed")
    public ResponseEntity getSpeedPollList(@RequestAttribute String uid){
        //String uid = "Wa7vHCo5wkND42B2iNWduigDKSy2";
        List<Optional> speedPolls = pollService.getSpeedPolls(uid);
        if (speedPolls.size() == 0) {
            return new ResponseEntity(DefaultRes.res(StatusCode.OK, "빠른투표 없음", speedPolls), HttpStatus.OK);
        }
        return new ResponseEntity(DefaultRes.res(StatusCode.OK, "빠른투표 반환 성공", speedPolls), HttpStatus.OK);
    }


    /**
     * 투표 삭제
     */
    @DeleteMapping("/{pollId}")
    public ResponseEntity deletePoll(@RequestAttribute String uid, @PathVariable("pollId") int pollId){
        //String uid = "Wa7vHCo5wkND42B2iNWduigDKSy2";
        int result = pollService.removePoll(uid, pollId);
        if (result == 0)
            return new ResponseEntity(DefaultRes.res(StatusCode.OK, "삭제 성공", pollId), HttpStatus.OK);
        else if (result == 1)
            return new ResponseEntity(DefaultRes.res(StatusCode.BAD_REQUEST, "삭제 실패(로그인유저-작성자 다름)"), HttpStatus.OK);
        else
            return new ResponseEntity(DefaultRes.res(StatusCode.NO_CONTENT, "삭제 실패(존재하지 않는 포스트)"), HttpStatus.OK);
    }
}
