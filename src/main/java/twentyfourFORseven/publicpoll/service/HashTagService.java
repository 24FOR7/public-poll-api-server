package twentyfourFORseven.publicpoll.service;

import org.springframework.stereotype.Service;
import twentyfourFORseven.publicpoll.domain.entity.HashTag;
import twentyfourFORseven.publicpoll.domain.repository.HashTagRepository;
import twentyfourFORseven.publicpoll.domain.repository.Mapping.HashTagMapping;
import twentyfourFORseven.publicpoll.domain.repository.Mapping.HashTagNameMapping;
import twentyfourFORseven.publicpoll.dto.HashTagDto;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class HashTagService {
    private HashTagRepository hashTagRepository;

    public HashTagService(HashTagRepository hashTagRepository) {
        this.hashTagRepository = hashTagRepository;
    }

    /**
     * hashTag를 조회하여 없으면 새로 생성해서 반환
     */
    @Transactional
    public HashTag findOrCreate(String hashTagName){
        Optional<HashTag> hashTag = hashTagRepository.findByName(hashTagName);
        if(hashTag.isEmpty()){
            hashTag = Optional.of(hashTagRepository.save(HashTagDto.builder().name(hashTagName).build().toEntity()));
        }
        return hashTag.get();
    }
    /**
     * 해시태그 스트링 리스트를 객체 리스트로 만들어주는 메서드
     * @param hashtags
     */
    public Set<HashTag> makeListOfHashTag(Set<String> hashtags){
        Set<HashTag> hashTagList = new HashSet<>();
        hashtags.forEach(hashtag -> {
            hashTagList.add(findOrCreate(hashtag));
        });
        return hashTagList;
    }

    public List<HashTagNameMapping> findHashTags(String keyword){
        return hashTagRepository.findAllByNameContaining(keyword);
    }

    public Optional<HashTagMapping> findHashTag(int id){
        return hashTagRepository.findOneById(id);
    }
}
