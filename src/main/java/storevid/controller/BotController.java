package storevid.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import storevid.config.Session;
import storevid.service.UpdateService;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class BotController extends TelegramWebhookBot {

    @Value("${telegram.bot.username}")
    private String botUsername;

    @Value("${telegram.bot.token}")
    private String botToken;

    @Value("${telegram.bot.path}")
    private String botPath;

    private final UpdateService updateService;

    @PostMapping
    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(@RequestBody Update update) {
        if (update.hasMessage()||update.hasCallbackQuery()) {
            Session.fetchData(update);
            return updateService.updateFromTelegram();
        } else {
            return null;
        }
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public String getBotPath() {
        return botPath;
    }
}
