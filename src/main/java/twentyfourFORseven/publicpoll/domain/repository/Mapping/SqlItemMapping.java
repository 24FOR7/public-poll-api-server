package twentyfourFORseven.publicpoll.domain.repository.Mapping;

public interface SqlItemMapping {
    Integer getId();
    Integer getPoll_id();
    Integer getItem_num();
    Boolean getHas_image();
    String getContents();
}
