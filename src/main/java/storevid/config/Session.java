package storevid.config;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import storevid.entity.Language;
import storevid.enums.Lang;
import storevid.repo.LangRepo;

import java.util.Optional;

@Component
@NoArgsConstructor
public class Session {

    public static String text;
    public static Integer messageId;
    public static String callbackData;
    public static boolean isCallback;
    public static boolean isText;

    @Autowired
    public void helper(LangRepo langRepo) {
        Session.langRepo = langRepo;
    }

    private static LangRepo langRepo;


    public static void fetchData(Update update) {
        if (update.hasCallbackQuery()) {
            isCallback = true;
            callbackData = update.getCallbackQuery().getData();
            messageId = update.getCallbackQuery().getMessage().getMessageId();
            buildSessionUser(update.getCallbackQuery().getMessage());
        } else if (update.hasMessage()) {
            if (update.getMessage().hasText()) {
                isText = true;
                text = update.getMessage().getText();
            }
            buildSessionUser(update.getMessage());
        }
    }

    private static void buildSessionUser(Message message) {
        SessionUser.firstname = message.getChat().getFirstName();
        SessionUser.lastName = message.getChat().getLastName();
        SessionUser.username = message.getChat().getUserName();
        SessionUser.chatId = message.getChatId();
        Optional<Language> languageOptional = langRepo.findByUser_ChatId(SessionUser.chatId);
        if (languageOptional.isPresent()) {
            SessionUser.lang = languageOptional.get().getLanguage();
        } else SessionUser.lang = Lang.EN;
    }
}
