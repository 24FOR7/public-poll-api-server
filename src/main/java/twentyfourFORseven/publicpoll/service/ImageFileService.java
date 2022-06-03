package twentyfourFORseven.publicpoll.service;

import twentyfourFORseven.publicpoll.domain.entity.ImageFile;
import twentyfourFORseven.publicpoll.domain.entity.Poll;
import twentyfourFORseven.publicpoll.domain.repository.ImageFileRepository;
import twentyfourFORseven.publicpoll.domain.repository.Mapping.PollMapping;
import twentyfourFORseven.publicpoll.domain.repository.PollRepository;
import twentyfourFORseven.publicpoll.dto.ImageFileDto;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class ImageFileService {
    private ImageFileRepository imageFileRepository;
    private PollRepository pollRepository;

    public ImageFileService(ImageFileRepository imageFileRepository, PollRepository pollRepository) {
        this.imageFileRepository = imageFileRepository;
        this.pollRepository = pollRepository;
    }

    @Transactional
    public Integer saveFile(ImageFileDto fileDto) {
        Optional<PollMapping> poll = pollRepository.findOneById(fileDto.getPoll_id());
        if (poll.get().getHasImage() && poll.get().getPresentImagePath() == null) {
            poll.get().setPresentImagePath("/images/images/"+fileDto.getPoll_id()+"/"+fileDto.getItem_num());
        }
        return imageFileRepository.save(fileDto.toEntity()).getId();
    }

    @Transactional
    public ImageFileDto getImageFile(Integer id, Integer poll_id, Integer item_number) {
        ImageFile imageFile = imageFileRepository.findById(id).get();

        ImageFileDto imageFileDto = ImageFileDto.builder()
                .id(id)
                .poll_id(poll_id)
                .item_num(item_number)
                .origFilename(imageFile.getOrigFilename())
                .filename(imageFile.getFilename())
                .filePath(imageFile.getFilePath())
                .build();
        return imageFileDto;
    }
}
