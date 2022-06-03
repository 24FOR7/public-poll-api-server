package twentyfourFORseven.publicpoll.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import twentyfourFORseven.publicpoll.RestApiResponse.DefaultRes;
import twentyfourFORseven.publicpoll.RestApiResponse.StatusCode;
import twentyfourFORseven.publicpoll.domain.entity.HashTag;
import twentyfourFORseven.publicpoll.domain.repository.Mapping.HashTagMapping;
import twentyfourFORseven.publicpoll.domain.repository.Mapping.HashTagNameMapping;
import twentyfourFORseven.publicpoll.service.HashTagService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/hashtag")
public class HashTagController {
    private HashTagService hashTagService;

    public HashTagController(HashTagService hashTagService) {
        this.hashTagService = hashTagService;
    }

    /**
     * 검색으로 해시태그 조회
     * LIKE 쿼리를 날려 %{keyword}%로 조회해서 반환
     */
    @GetMapping("/name/{keyword}")
    public ResponseEntity searchHashTags(@PathVariable("keyword") String keyword){
        List<HashTagNameMapping> hashTags = hashTagService.findHashTags(keyword);
        if(hashTags.size() != 0) {
            String resMessage = "해시태그 반환 성공";
            return new ResponseEntity(DefaultRes.res(StatusCode.OK, resMessage, hashTags), HttpStatus.OK);
        }
        String resMessage = "검색된 해시태그 없음";
        return new ResponseEntity(DefaultRes.res(StatusCode.BAD_REQUEST, resMessage, hashTags), HttpStatus.OK);
    }


    /**
     * searchHashTag's' 로 조회 한 후 id로 검색하면 그때 해시태그에 포함된 게시글 반환
     */
    @GetMapping("/id/{id}")
    public ResponseEntity searchHashTags(@PathVariable("id") int id){
        Optional<HashTagMapping> hashTag = hashTagService.findHashTag(id);
        if(hashTag.isPresent()) {
            if(hashTag.get().getPolls().size() == 0){   //해시태그는 존재하지만 포함된 투표가 없을경우
                String resMessage = "해시태그에 포함된 투표 없음";
                return new ResponseEntity(DefaultRes.res(StatusCode.OK, resMessage, hashTag.get()), HttpStatus.OK);
            }
            String resMessage = "해시태그, 투표 반환 성공";   //해시태그와 포함된 투표 모두 존재할 경우
            return new ResponseEntity(DefaultRes.res(StatusCode.OK, resMessage, hashTag.get()), HttpStatus.OK);
        }
        String resMessage = "검색된 해시태그 없음";  //해시태그가 존재하지 않을경우
        return new ResponseEntity(DefaultRes.res(StatusCode.BAD_REQUEST, resMessage), HttpStatus.OK);
    }
}
