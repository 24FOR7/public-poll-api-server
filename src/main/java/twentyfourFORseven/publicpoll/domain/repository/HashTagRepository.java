package twentyfourFORseven.publicpoll.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import twentyfourFORseven.publicpoll.domain.entity.HashTag;
import twentyfourFORseven.publicpoll.domain.repository.Mapping.HashTagMapping;
import twentyfourFORseven.publicpoll.domain.repository.Mapping.HashTagNameMapping;

import java.util.List;
import java.util.Optional;

public interface HashTagRepository extends JpaRepository<HashTag, Integer> {
    Optional<HashTag> findByName(String name);
    List<HashTagNameMapping> findAllByNameContaining(@Param("keyword") String keyword);
    /**
     * findBy${}Containing 해당 메서드로 like조회를 하게되는데
     * 현재 hibernate-core에서 문제가 발생하여 2번째 조회 시 에러가 뜬다.
     * 문제 버전은 5.6.6.Final에서 발생한다고 한다.
     * 따라서 임시로 위와같이 @Param으로 매개변수를 설정하여 문제를 방지
     * 해당 이슈 링크
     * https://github.com/spring-projects/spring-data-jpa/issues/2476
     */

    Optional<HashTagMapping> findOneById(int id);
}
