package storevid.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import storevid.entity.Location;

import java.util.UUID;

@Repository
public interface LocationRepo extends JpaRepository<Location, UUID> {
}
