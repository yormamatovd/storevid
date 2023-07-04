package storevid.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.CopyMessage;
import org.telegram.telegrambots.meta.api.methods.ForwardMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageCaption;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import storevid.controller.BotController;
import storevid.service.SenderService;

@Service
public class SenderServiceImpl implements SenderService {

    @Lazy
    @Autowired
    private BotController botController;


    @Override
    public void sendMessage(SendMessage sendMessage) {
        try {
            botController.execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void copyMessage(CopyMessage copyMessage) {
        try {
            botController.execute(copyMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendPhoto(SendPhoto sendPhoto) {
        try {
            botController.execute(sendPhoto);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void forwardMessage(ForwardMessage forwardMessage) {
        try {
            botController.execute(forwardMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void editMessageCaption(EditMessageCaption editMessageCaption) {
        try {
            botController.execute(editMessageCaption);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteMessage(Long chatId, Integer messageId) {
        try {
            botController.execute(new DeleteMessage(chatId+"",messageId));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
