package twentyfourFORseven.publicpoll.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import twentyfourFORseven.publicpoll.domain.entity.User;

import java.util.Optional;

//                                                   <Entity, Id Type>
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByNick(String nick);
    Optional<User> findByUid(String uid);
}
