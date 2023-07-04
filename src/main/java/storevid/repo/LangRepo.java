package storevid.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import storevid.entity.Language;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface LangRepo extends JpaRepository<Language, UUID> {

    Optional<Language> findByUser_ChatId(Long user_chatId);
}
