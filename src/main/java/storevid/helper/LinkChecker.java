package storevid.helper;

public class LinkChecker {
    public static boolean isValid(String link) {
        if (isVideoLink1(link)) return true;
        else return isVideoLink2(link);
    }

    private static boolean isVideoLink1(String link) {
        return link.startsWith("https://youtu.be/");
    }

    private static boolean isVideoLink2(String link) {
        return link.startsWith("https://www.youtube.com/watch?v=");
    }
}
