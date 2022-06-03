package twentyfourFORseven.publicpoll.service;

import org.springframework.stereotype.Service;
import twentyfourFORseven.publicpoll.domain.entity.Item;
import twentyfourFORseven.publicpoll.domain.repository.BallotRepository;
import twentyfourFORseven.publicpoll.domain.repository.ItemRepository;
import twentyfourFORseven.publicpoll.domain.repository.Mapping.PollMapping;
import twentyfourFORseven.publicpoll.domain.repository.Mapping.SqlBallotMapping;
import twentyfourFORseven.publicpoll.domain.repository.Mapping.SqlBallotStatMapping;
import twentyfourFORseven.publicpoll.domain.repository.Mapping.StatMapping;
import twentyfourFORseven.publicpoll.domain.repository.PollRepository;
import twentyfourFORseven.publicpoll.domain.repository.UserRepository;
import twentyfourFORseven.publicpoll.dto.GetPollStatDto;
import twentyfourFORseven.publicpoll.dto.StatDto;
import twentyfourFORseven.publicpoll.dto.StatPageDto;

import javax.persistence.criteria.CriteriaBuilder;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StatService {
    private PollRepository pollRepository;
    private ItemRepository itemRepository;
    private UserRepository userRepository;
    private BallotRepository ballotRepository;

    public StatService(PollRepository pollRepository, ItemRepository itemRepository, UserRepository userRepository, BallotRepository ballotRepository) {
        this.pollRepository = pollRepository;
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
        this.ballotRepository = ballotRepository;
    }


    /**
     * 밸롯 추가
     */
    @Transactional
    public List<StatPageDto> getPollStatByPollId(GetPollStatDto getPollStatDto) {
        List<StatPageDto> stats = new ArrayList<>();

        // 해당 poll 가져오기
        Optional<PollMapping> poll = pollRepository.findOneById(getPollStatDto.getPollId());

        // poll의 item 가져오기
        List<Item> items = itemRepository.findAllByPollId(poll.get().getId());
        int itemCnt = items.size();

        if (poll.isPresent()) {
            List<SqlBallotStatMapping> ballots = null;

            System.out.println("- " + getPollStatDto.getAgeOption() + " " + getPollStatDto.getGenderOption() + " " + getPollStatDto.getTierOption());

            // 조건에 맞는 쿼리
            int queryOption = -1;
            if (getPollStatDto.getAgeOption() == 0 && getPollStatDto.getGenderOption() == 0 && getPollStatDto.getTierOption() == 0) queryOption = 0;
            else if (getPollStatDto.getAgeOption() != 0 && getPollStatDto.getGenderOption() == 0 && getPollStatDto.getTierOption() == 0) queryOption = 1;
            else if (getPollStatDto.getAgeOption() == 0 && getPollStatDto.getGenderOption() != 0 && getPollStatDto.getTierOption() == 0) queryOption = 2;
            else if (getPollStatDto.getAgeOption() == 0 && getPollStatDto.getGenderOption() == 0 && getPollStatDto.getTierOption() != 0) queryOption = 3;
            else if (getPollStatDto.getAgeOption() != 0 && getPollStatDto.getGenderOption() != 0 && getPollStatDto.getTierOption() == 0) queryOption = 4;
            else if (getPollStatDto.getAgeOption() == 0 && getPollStatDto.getGenderOption() != 0 && getPollStatDto.getTierOption() != 0) queryOption = 5;
            else if (getPollStatDto.getAgeOption() != 0 && getPollStatDto.getGenderOption() == 0 && getPollStatDto.getTierOption() != 0) queryOption = 6;
            else if (getPollStatDto.getAgeOption() != 0 && getPollStatDto.getGenderOption() != 0 && getPollStatDto.getTierOption() != 0) queryOption = 7;

            System.out.println("qoption : " + queryOption);

            int optionBallotsSum = 0;
            for (int i = 1; i <= itemCnt; i++) {
                char gender = getPollStatDto.getGenderOption() == 0? 'o' : getPollStatDto.getGenderOption() == 1? 'm' : 'f';
                switch(queryOption){
                    case 0:
                        System.out.println(i+ " "+"total");
                        ballots = ballotRepository.findAllByPollIdAndItemNum(getPollStatDto.getPollId(), i);
                        break;
                    case 1:
                        System.out.println(i+ " "+"id, age");
                        ballots = ballotRepository.findAllQueryByAge(getPollStatDto.getPollId(), i, getPollStatDto.getAgeOption());
                        break;
                    case 2:
                        System.out.println(i+ " "+"gender");
                        ballots = ballotRepository.findAllQueryByGender(getPollStatDto.getPollId(), i, gender);
                        break;
                    case 3:
                        System.out.println(i+ " "+"tier");
                        ballots = ballotRepository.findAllQueryByTier(getPollStatDto.getPollId(), i, getPollStatDto.getTierOption());
                        break;
                    case 4:
                        System.out.println(i+ " "+"age, gender");
                        ballots = ballotRepository.findAllQueryByAgeAndGender(getPollStatDto.getPollId(), i, getPollStatDto.getAgeOption(), gender);
                        break;
                    case 5:
                        System.out.println(i+ " "+"gender tier");
                        ballots = ballotRepository.findAllQueryByGenderAndTier(getPollStatDto.getPollId(), i, gender, getPollStatDto.getTierOption());
                        break;
                    case 6:
                        System.out.println(i+ " "+"tier age");
                        ballots = ballotRepository.findAllQueryByTierAndAge(getPollStatDto.getPollId(), i, getPollStatDto.getTierOption(), getPollStatDto.getAgeOption());
                        break;
                    case 7:
                        System.out.println(i+ " "+"all jogun");
                        ballots = ballotRepository.findAllQueryByAgeAndGenderAndTier(getPollStatDto.getPollId(), i, getPollStatDto.getAgeOption(), gender, getPollStatDto.getTierOption());
                        break;
                }
                optionBallotsSum += ballots.size();
                int j = 0;
                for (j = 0; j < items.size(); j++) {
                    if (items.get(j).getItemNum() == i) break;
                }
                stats.add(new StatPageDto(i, items.get(j).getContents(), 0, ballots.size(), 0.0));
            }


            // 연산해서 넣기
            for (int i = 0; i < itemCnt; i++) {
                System.out.println("top : "+ stats.get(i).getOptionItemCnt()+"");
                System.out.println("under : " + optionBallotsSum+"");
                stats.get(i).setOptionTotalCnt(optionBallotsSum);
                if (optionBallotsSum == 0) stats.get(i).setPercent(0.0);
                else stats.get(i).setPercent( (double)stats.get(i).getOptionItemCnt()  / (double)optionBallotsSum );
            }

            return stats;
        }
        return new ArrayList<>();
    }
}
