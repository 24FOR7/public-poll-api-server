package twentyfourFORseven.publicpoll.dto;

import lombok.*;
import twentyfourFORseven.publicpoll.domain.entity.ImageFile;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ImageFileDto {
    private Integer id;
    private Integer poll_id;
    private Integer item_num;
    private String origFilename;
    private String filename;
    private String filePath;

    public ImageFile toEntity() {
        ImageFile build = ImageFile.builder()
                .id(id)
                .poll_id(poll_id)
                .item_num(item_num)
                .origFilename(origFilename)
                .filename(filename)
                .filePath(filePath)
                .build();
        return build;
    }

    @Builder
    public ImageFileDto(Integer id, Integer poll_id, Integer item_num,String origFilename, String filename, String filePath) {
        this.id = id;
        this.poll_id = poll_id;
        this.item_num = item_num;
        this.origFilename = origFilename;
        this.filename = filename;
        this.filePath = filePath;
    }
}
