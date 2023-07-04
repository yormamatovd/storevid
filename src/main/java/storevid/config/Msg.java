package storevid.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class Msg {

    private static MessageSource messageSource;

    @Autowired
    public void helper(MessageSource messageSource) {
        Msg.messageSource = messageSource;
    }

    public static String get(String key) {
        return messageSource.getMessage(key, null, new Locale(SessionUser.lang.name().toLowerCase(Locale.ROOT)));
    }
}
