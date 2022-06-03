package twentyfourFORseven.publicpoll.domain.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ImageFile {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(nullable = false)
    private Integer poll_id;

    @Column(nullable = false)
    private Integer item_num;

    @Column(nullable = false)
    private String origFilename;

    @Column(nullable = false)
    private String filename;

    @Column(nullable = false)
    private String filePath;

    @Builder
    public ImageFile(Integer id, Integer poll_id, Integer item_num,String origFilename, String filename, String filePath) {
        this.id = id;
        this.poll_id = poll_id;
        this.item_num = item_num;
        this.origFilename = origFilename;
        this.filename = filename;
        this.filePath = filePath;
    }
}
