package twentyfourFORseven.publicpoll.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class StatDto {
    Integer itemNum;
    Integer optionTotalCnt;
    Integer optionItemCnt;
    Double percent;
    Boolean isBest = false;

    public StatDto(Integer itemNum, Integer optionTotalCnt, Integer optionItemCnt, Double percent) {
        this.itemNum = itemNum;
        this.optionTotalCnt = optionTotalCnt;
        this.optionItemCnt = optionItemCnt;
        this.percent = percent;
    }
}
