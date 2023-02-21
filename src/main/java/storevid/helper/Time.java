package storevid.helper;

import java.time.Instant;

public class Time {

    public static Long getCurrentUTCSeconds() {
        return Instant.now().getEpochSecond();
    }
}
