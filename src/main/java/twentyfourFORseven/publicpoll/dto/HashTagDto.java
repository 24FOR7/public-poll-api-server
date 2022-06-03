package twentyfourFORseven.publicpoll.dto;

import lombok.*;
import twentyfourFORseven.publicpoll.domain.entity.HashTag;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class HashTagDto {
    private int id;
    private String name;

    public HashTag toEntity(){
        return HashTag.builder()
                .id(id)
                .name(name)
                .build();
    }

    @Builder
    public HashTagDto(String name){
        this.name = name;
    }
}
