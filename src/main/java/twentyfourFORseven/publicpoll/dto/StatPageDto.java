package twentyfourFORseven.publicpoll.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class StatPageDto {
    Integer itemNum;
    String contents;
    Integer optionTotalCnt;
    Integer optionItemCnt;
    Double percent;

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public StatPageDto(Integer itemNum, String contents, Integer optionTotalCnt, Integer optionItemCnt, Double percent) {
        this.itemNum = itemNum;
        this.contents = contents;
        this.optionTotalCnt = optionTotalCnt;
        this.optionItemCnt = optionItemCnt;
        this.percent = percent;
    }
}
