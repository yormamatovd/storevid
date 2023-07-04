package storevid.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import storevid.config.SessionUser;
import storevid.entity.Language;
import storevid.entity.User;
import storevid.enums.Lang;
import storevid.repo.LangRepo;
import storevid.repo.UserRepo;
import storevid.service.UserService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    private final LangRepo langRepo;

    @Transactional
    @Override
    public void updateUserInfo() {
        Optional<User> userOptional = userRepo.findByChatId(SessionUser.chatId);
        User user;
        if (userOptional.isEmpty()) {
            user = new User();
            user.setChatId(SessionUser.chatId);
        } else {
            user = userOptional.get();
        }
        user.setFirstname(SessionUser.firstname);
        user.setLastname(SessionUser.lastName);
        user.setUsername(SessionUser.username);
        userRepo.save(user);

        langRepo.save(new Language(user, Lang.EN));
    }
}
