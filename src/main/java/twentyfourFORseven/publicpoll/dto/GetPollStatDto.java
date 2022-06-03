package twentyfourFORseven.publicpoll.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class GetPollStatDto {
    private Integer id;
    private Integer pollId;
    private Integer ageOption;
    private Integer genderOption;
    private Integer tierOption;

    // poll id
    // 나이대
    // 성별
    // 티어

    @Builder
    public GetPollStatDto(Integer pollId, Integer ageOption, Integer genderOption, Integer tierOption) {
        this.pollId = pollId;
        this.ageOption = ageOption;
        this.genderOption = genderOption;
        this.tierOption = tierOption;
    }
}
