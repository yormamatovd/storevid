package storevid.helper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Component
public class Loader implements CommandLineRunner {

    @Value("${telegram.bot.path}")
    private String botPath;
    @Value("${telegram.bot.token}")
    private String botToken;

    @Override
    public void run(String... args) {
        webhookUpdater(botPath, botToken);
    }

    private void webhookUpdater(String webhookUrl, String botToken) {
        try {
            URL url = new URL(" https://api.telegram.org/bot" + botToken + "/setWebhook?url=" + webhookUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                System.out.println(response);
                if (!response.toString().contains("\"result\":true")) throw new RuntimeException("Webhook not set");
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
