package storevid.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import storevid.entity.WaitRequest;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface WaitRequestRepo extends JpaRepository<WaitRequest,Long> {

    Optional<WaitRequest> findByRequestSSID(UUID requestSSID);
}
