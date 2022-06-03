package twentyfourFORseven.publicpoll.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import twentyfourFORseven.publicpoll.RestApiResponse.DefaultRes;
import twentyfourFORseven.publicpoll.RestApiResponse.StatusCode;
import twentyfourFORseven.publicpoll.domain.repository.Mapping.CommentMapping;
import twentyfourFORseven.publicpoll.domain.repository.Mapping.PollMapping;
import twentyfourFORseven.publicpoll.domain.repository.Mapping.StatMapping;
import twentyfourFORseven.publicpoll.dto.GetPollStatDto;
import twentyfourFORseven.publicpoll.dto.StatDto;
import twentyfourFORseven.publicpoll.dto.StatPageDto;
import twentyfourFORseven.publicpoll.service.StatService;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/stat")
public class StatController {
    private StatService statService;

    public StatController(StatService statService) {
        this.statService = statService;
    }

    /**
     * 특정 투표 통계 반환
     * */
    @PostMapping("/")
    public ResponseEntity getStat(@RequestBody GetPollStatDto getPollStatDto) {

        System.out.println("---------------------------------------------------------호출됨");
        List<StatPageDto> pollStats = statService.getPollStatByPollId(getPollStatDto);
        System.out.println("---------------------------------------------------------크기 : " + pollStats.size());

        if (pollStats.size() != 0) {
            String resMessage = "통계 반환 완료";
            return new ResponseEntity(DefaultRes.res(StatusCode.OK, resMessage, pollStats), HttpStatus.OK);
        }
        String resMessage = "통계 반환 실패";
        return new ResponseEntity(DefaultRes.res(StatusCode.BAD_REQUEST, resMessage, pollStats), HttpStatus.OK);

    }
}
