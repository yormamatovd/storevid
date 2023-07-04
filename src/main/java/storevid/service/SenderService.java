package storevid.service;

import org.telegram.telegrambots.meta.api.methods.CopyMessage;
import org.telegram.telegrambots.meta.api.methods.ForwardMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageCaption;

public interface SenderService {

    void sendMessage(SendMessage sendMessage);

    void copyMessage(CopyMessage copyMessage);

    void sendPhoto(SendPhoto sendPhoto);

    void forwardMessage(ForwardMessage forwardMessage);

    void editMessageCaption(EditMessageCaption editMessageCaption);

    void deleteMessage(Long chatId, Integer messageId);
}
