package storevid.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import storevid.config.Session;
import storevid.service.*;

@Service
@RequiredArgsConstructor
public class UpdateServiceImpl implements UpdateService {

    private final UserService userService;
    private final TextService textService;
    private final CallBackService callBackService;


    @Override
    public BotApiMethod<?> updateFromTelegram() {
        userService.updateUserInfo();

        if (Session.isText) {
            textService.text();
        } else if (Session.isCallback) {
            callBackService.callback();
        }

        return null;
    }
}
