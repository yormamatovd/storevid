package storevid.service;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;

public interface UpdateService {


    BotApiMethod<?> updateFromTelegram();
}
