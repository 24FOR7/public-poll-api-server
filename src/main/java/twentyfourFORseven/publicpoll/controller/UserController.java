package twentyfourFORseven.publicpoll.controller;

import com.google.api.Http;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import twentyfourFORseven.publicpoll.RestApiResponse.DefaultRes;
import twentyfourFORseven.publicpoll.RestApiResponse.StatusCode;
import twentyfourFORseven.publicpoll.domain.entity.User;
import twentyfourFORseven.publicpoll.dto.UserDto;
import twentyfourFORseven.publicpoll.service.UserService;

import javax.swing.text.html.Option;
import java.util.Map;
import java.util.Optional;

@RestController //하위에 있는 메소드들은 ResponseBody
@RequestMapping("/user")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 닉네임 중복 체크
     * 변경 가능 -> return true
     * 변경 불가능 -> return false
     */
    @PostMapping("/checkNick")
    public ResponseEntity checkDuplicateNick(@RequestBody Map<String, String> body){
        if(userService.validateDuplicateNick(body.get("nick")))    //닉네임 중복된 경우
            return new ResponseEntity(DefaultRes.res(StatusCode.BAD_REQUEST, "닉네임 사용 불가능", false), HttpStatus.OK);
        else    //닉네임 사용 가능한 경함
            return new ResponseEntity(DefaultRes.res(StatusCode.OK, "닉네임 사용 가능", true), HttpStatus.OK);
    }

    /**
     * 회원가입
     */
    @PostMapping("/signUp")
    public ResponseEntity signUp(@RequestAttribute String uid, @RequestBody UserDto userDto){
        userDto.setUid(uid);    //uid 저장
        Optional<User> newUser = userService.join(userDto);

        if(newUser.isPresent()){
            String resMessage = "회원가입 성공";
            return new ResponseEntity(DefaultRes.res(StatusCode.OK, resMessage, userService.getUserInfo(newUser.get())), HttpStatus.OK);
        }else{
            String resMessage = "회원가입 실패";
            return new ResponseEntity(DefaultRes.res(StatusCode.BAD_REQUEST, resMessage), HttpStatus.OK);
        }
    }

    /**
     * 로그인 -> 파이어베이스에서 진행함
     * 서버는 해당 유저의 디비가 있으면 접속 허용
     */
    @PostMapping("/signIn")
    public ResponseEntity signIn(@RequestAttribute String uid){
        Optional<User> user = userService.findUser(uid);

        return user.map(value -> new ResponseEntity(DefaultRes.res(StatusCode.OK, "로그인 성공", userService.getUserInfo(value)), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity(DefaultRes.res(StatusCode.BAD_REQUEST, "로그인 실패"), HttpStatus.OK));
    }

    /**
     * 마이페이지에 쓰일 유저 정보 반환
     */
    @GetMapping("/myPage")
    public ResponseEntity getUserData(@RequestAttribute String uid){
        Optional<User> user = userService.findUser(uid);

        if(user.isPresent()){
            String resMessage = "조회 성공";
            return new ResponseEntity(DefaultRes.res(StatusCode.OK, resMessage, userService.getUserInfo(user.get())), HttpStatus.OK);
        }else{
            String resMessage = "조회 실패 : 검색되지 않는 유저";
            return new ResponseEntity(DefaultRes.res(StatusCode.BAD_REQUEST, resMessage), HttpStatus.OK);
        }
    }

    /**
     * 유저 정보 업데이트
     */
    @PostMapping("/myPage")
    public ResponseEntity updateUserData(@RequestAttribute String uid, @RequestBody UserDto userDto){
        Optional<User> updatedUser = userService.updateUserInfo(uid, userDto.toEntity());
        if(updatedUser.isPresent()){
            String resMessage = "유저 정보 변경 성공";
            return new ResponseEntity(DefaultRes.res(StatusCode.OK, resMessage, userService.getUserInfo(updatedUser.get())), HttpStatus.OK);
        }else{
            String resMessage = "유저 정보 변경 실패";
            return new ResponseEntity(DefaultRes.res(StatusCode.BAD_REQUEST, resMessage), HttpStatus.OK);
        }
    }
}
