package twentyfourFORseven.publicpoll.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import twentyfourFORseven.publicpoll.domain.entity.ImageFile;

public interface ImageFileRepository extends JpaRepository<ImageFile, Integer> {
}