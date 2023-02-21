package storevid.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import storevid.entity.Referal;

import java.util.UUID;

@Repository
public interface ReferalRepo extends JpaRepository<Referal, UUID> {
}
