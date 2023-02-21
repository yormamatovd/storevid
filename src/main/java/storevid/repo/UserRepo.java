package storevid.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import storevid.entity.User;

import java.util.UUID;

@Repository
public interface UserRepo extends JpaRepository<User, UUID> {

}
