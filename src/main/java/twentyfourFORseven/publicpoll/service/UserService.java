package twentyfourFORseven.publicpoll.service;

import org.springframework.stereotype.Service;
import twentyfourFORseven.publicpoll.domain.entity.User;
import twentyfourFORseven.publicpoll.domain.repository.BallotRepository;
import twentyfourFORseven.publicpoll.domain.repository.Mapping.MyVotedPollMapping;
import twentyfourFORseven.publicpoll.domain.repository.PollRepository;
import twentyfourFORseven.publicpoll.domain.repository.UserRepository;
import twentyfourFORseven.publicpoll.dto.UserDto;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;
    private BallotRepository ballotRepository;
    private PollRepository pollRepository;

    public UserService(UserRepository userRepository, BallotRepository ballotRepository, PollRepository pollRepository) {
        this.userRepository = userRepository;
        this.ballotRepository = ballotRepository;
        this.pollRepository = pollRepository;
    }


    /**
     * 회원가입 -> 성공하면 닉네임 반환
     */
    @Transactional
    public Optional<User> join(UserDto userDto){
        if(!validateDuplicateNick(userDto.getNick())) {  //유저 닉네임 중복 체크
            User newUser = userRepository.save(userDto.toEntity());
            return Optional.of(newUser);
        }else{
            System.out.println("중복");
            return Optional.empty();
        }
    }

    /**
     * 닉네임 중복체크
     * 중복 -> true
     */
    public boolean validateDuplicateNick(String nick){
        return userRepository.findByNick(nick).isPresent();
    }

    /**
     * 유저 객체 전체 반환 (uid검색)
     */
    public Optional<User> findUser(String uid){
        return userRepository.findByUid(uid);
    }

    /**
     * 유저 정보 반환(uid, Data 관련 안가져옴
     */
    public User getUserInfo(User user){
        return User.builder()
                .email(user.getEmail())
                .nick(user.getNick())
                .age(user.getAge())
                .gender(user.getGender())
                .tier(user.getTier())
                .userInterest1(user.getUserInterest1())
                .userInterest2(user.getUserInterest2())
                .userInterest3(user.getUserInterest3())
                .build();
    }

    /**
     * 유저 정보 업데이트
     */
    @Transactional
    public Optional<User> updateUserInfo(String uid, User updateData){
        Optional<User> originData = userRepository.findByUid(uid);
        if(originData.isPresent()) {
            User user = originData.get();
            user.setEmail(updateData.getEmail());
            if (!validateDuplicateNick(updateData.getNick()))    //닉네임 중복된 경우 변경 안함
                user.setNick(updateData.getNick());
            user.setAge(updateData.getAge());
            user.setGender(updateData.getGender());
            user.setUserInterest1(updateData.getUserInterest1());
            user.setUserInterest2(updateData.getUserInterest2());
            user.setUserInterest3(updateData.getUserInterest3());
            userRepository.save(user);
            return Optional.of(user);
        }
        return Optional.empty();
    }

    /**
     * 유저 정보 업데이트
     */
    @Transactional
    public Optional<User> updateTier(String uid){
        Optional<User> originData = userRepository.findByUid(uid);
        if(originData.isPresent()) {
            User user = originData.get();

            int sum = pollRepository.findAllByUser(originData.get()).size() + ballotRepository.findAllByUser(originData.get()).size();  // 내가 올린 투표수 + 내가 참여한 투표수

            if (sum < 5) user.setTier(1);               // bronze
            else if (sum < 10) user.setTier(2);        // silver
            else if (sum < 15) user.setTier(3);        // gold
            else if (sum < 25) user.setTier(4);        // platinum
            else user.setTier(5);                       // diamond

            userRepository.save(user);
            return Optional.of(user);
        }
        return Optional.empty();
    }
}
