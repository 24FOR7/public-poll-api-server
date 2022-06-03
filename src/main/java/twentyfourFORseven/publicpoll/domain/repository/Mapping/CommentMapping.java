package twentyfourFORseven.publicpoll.domain.repository.Mapping;

public interface CommentMapping {
    Integer getId();
    Whatever getUser();
    Whatever2 getPoll();
    String getContents();


    interface Whatever{
        String getNick();
        Integer getTier();
    }
    interface Whatever2{
        int getId();
        String getContents();
    }
}
