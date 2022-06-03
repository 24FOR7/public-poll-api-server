package twentyfourFORseven.publicpoll.domain.repository.Mapping;

public interface BallotMapping {
    Integer getId();
    Whatever getUser();
    Whatever2 getPoll();
    Integer getItemNum();


    interface Whatever{
        String getNick();
        Integer getTier();
    }
    interface Whatever2{
        int getId();
        String getContents();
    }
}

