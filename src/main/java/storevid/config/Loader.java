package storevid.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import storevid.service.RestTemplateService;

@Component
@RequiredArgsConstructor
public class Loader implements CommandLineRunner {

    private final RestTemplateService restTemplateService;


    @Override
    public void run(String... args) {
        restTemplateService.setTelegramWebhook();
        restTemplateService.setStVidServerWebhook();
    }
}
