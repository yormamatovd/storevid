package storevid.config;

import storevid.enums.Lang;

public class SessionUser {
    public static String firstname;
    public static String lastName;
    public static String username;
    public static Long chatId;
    public static Lang lang;

    public static String getChatIdString() {
        return chatId+"";
    }

}
