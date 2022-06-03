package twentyfourFORseven.publicpoll.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import twentyfourFORseven.publicpoll.RestApiResponse.DefaultRes;
import twentyfourFORseven.publicpoll.RestApiResponse.StatusCode;
import twentyfourFORseven.publicpoll.domain.repository.Mapping.BallotMapping;
import twentyfourFORseven.publicpoll.domain.repository.Mapping.MyVotedPollMapping;
import twentyfourFORseven.publicpoll.domain.repository.UserRepository;
import twentyfourFORseven.publicpoll.dto.AddBallotDto;
import twentyfourFORseven.publicpoll.dto.StatDto;
import twentyfourFORseven.publicpoll.service.BallotService;
import twentyfourFORseven.publicpoll.service.UserService;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
@RequestMapping("/ballot")
public class BallotController {
    private BallotService ballotService;
    private UserService userService;

    public BallotController(BallotService ballotService, UserService userService) {
        this.ballotService = ballotService;
        this.userService = userService;
    }

    /**
     * 밸롯 생성
     */
    @PostMapping("/add")
    public ResponseEntity createBallot(@RequestAttribute String uid, @RequestBody AddBallotDto addBallotDto){
        //String uid = "Wa7vHCo5wkND42B2iNWduigDKSy2";

        List<StatDto> newBallot = ballotService.addBallot(uid, addBallotDto);

        if (newBallot.size() != 0){
            String resMessage = "밸롯 생성 완료";
            userService.updateTier(uid);
            return new ResponseEntity(DefaultRes.res(StatusCode.OK, resMessage, newBallot), HttpStatus.OK);
        }
        String resMessage = "밸롯 생성 실패";
        return new ResponseEntity(DefaultRes.res(StatusCode.BAD_REQUEST, resMessage), HttpStatus.OK);
    }

    /**
     * 밸롯 재생성 (재투표)
     */
    @PostMapping("/revote")
    public ResponseEntity revoteBallot(@RequestAttribute String uid, @RequestBody AddBallotDto addBallotDto){
        //String uid = "Wa7vHCo5wkND42B2iNWduigDKSy2";
        //System.out.println("밸롯 컨트롤러 실행");

        List<StatDto> newBallot = ballotService.revoteBallot(uid, addBallotDto);

        if (newBallot.size() != 0){
            String resMessage = "밸롯 재생성 완료";
            return new ResponseEntity(DefaultRes.res(StatusCode.OK, resMessage, newBallot), HttpStatus.OK);
        }
        String resMessage = "밸롯 재생성 실패";
        return new ResponseEntity(DefaultRes.res(StatusCode.BAD_REQUEST, resMessage), HttpStatus.OK);
    }

    /**
     * 투표 해당 밸롯 조회
     **/
    @GetMapping("/id/{pollId}")
    public ResponseEntity getBallotByPollId(@PathVariable("pollId") int id){
        List<BallotMapping> ballot = ballotService.getBallotByPollId(id);
        if(ballot.size() != 0) {
            String resMessage = "밸롯 반환 성공";
            return new ResponseEntity(DefaultRes.res(StatusCode.OK, resMessage, ballot), HttpStatus.OK);
        }
        String resMessage = "밸록 존재하지 않습니다";  //해시태그가 존재하지 않을경우
        return new ResponseEntity(DefaultRes.res(StatusCode.BAD_REQUEST, resMessage), HttpStatus.OK);
    }

    /**
     * myBallot 리스트 반환
     */
    @GetMapping("/my")
    public ResponseEntity getMyBallots(@RequestAttribute String uid){
        List<MyVotedPollMapping> votedPolls = ballotService.findVotedPollId(uid);
        return new ResponseEntity(DefaultRes.res(StatusCode.OK, "resMessage", votedPolls), HttpStatus.OK);
    }

}
