package twentyfourFORseven.publicpoll.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import twentyfourFORseven.publicpoll.RestApiResponse.DefaultRes;
import twentyfourFORseven.publicpoll.RestApiResponse.StatusCode;
import twentyfourFORseven.publicpoll.domain.repository.Mapping.CommentMapping;
import twentyfourFORseven.publicpoll.dto.AddCommentDto;
import twentyfourFORseven.publicpoll.service.CommentService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/comment")
public class CommentController {
    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    /**
     * 댓글 생성
     */
    @PostMapping("/add")
    public ResponseEntity createPoll(/*@RequestAttribute String uid,*/ @RequestBody AddCommentDto addCommentDto){
        String uid = "Wa7vHCo5wkND42B2iNWduigDKSy2";

        Optional<CommentMapping> newComment = commentService.addComment(uid, addCommentDto);

        if (newComment.isPresent()){
            String resMessage = "댓글 생성 완료";
            return new ResponseEntity(DefaultRes.res(StatusCode.OK, resMessage, newComment.get()), HttpStatus.OK);
        }
        String resMessage = "댓글 생성 실패";
        return new ResponseEntity(DefaultRes.res(StatusCode.BAD_REQUEST, resMessage), HttpStatus.OK);
    }

    /**
    * 게시글에 해당하는 댓글 조회
    **/
    @GetMapping("/id/{pollId}")
    public ResponseEntity getCommentByPollId(@PathVariable("pollId") int id){
        List<CommentMapping> comment = commentService.getCommentByPollId(id);
        if(comment.size() != 0) {
            String resMessage = "댓글 반환 성공";
            return new ResponseEntity(DefaultRes.res(StatusCode.OK, resMessage, comment), HttpStatus.OK);
        }
        String resMessage = "댓글이 존재하지 않습니다";  //해시태그가 존재하지 않을경우
        return new ResponseEntity(DefaultRes.res(StatusCode.BAD_REQUEST, resMessage, new ArrayList<>()), HttpStatus.OK);
    }



}
