package twentyfourFORseven.publicpoll.dto;

import lombok.*;
import twentyfourFORseven.publicpoll.domain.entity.Item;
import twentyfourFORseven.publicpoll.domain.entity.Poll;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class AddItemDto {
    private Integer id;
    //private Poll poll;
    private Integer itemNum;
    private String contents;
    private boolean hasImage;

    public Item toEntity(Poll poll) {
        return Item.builder()
                .id(id)
                .poll(poll)
                .itemNum(itemNum)
                .contents(contents)
                .hasImage(hasImage)
                .build();
    }

    @Builder
    public AddItemDto(Integer itemNum, String contents, Boolean hasImage) {
        this.itemNum = itemNum;
        this.contents = contents;
        this.hasImage = hasImage;
    }
}
