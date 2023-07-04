package storevid.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import storevid.dao.InfoResDto;
import storevid.dao.StoreVidInfoDto;
import storevid.service.RestTemplateService;

@Service
@RequiredArgsConstructor
public class RestTemplateServiceImpl implements RestTemplateService {

    private final RestTemplate restTemplate;

    @Value("${st-vid.remote}")
    private String remoteServiceHost;
    @Value("${st-vid.own}")
    private String stVidWebhook;
    @Value("${st-vid.key}")
    private String remoteServiceKey;

    @Value("${telegram.bot.token}")
    private String botToken;
    @Value("${telegram.bot.path}")
    private String telegramBotWebhook;



    @Override
    public ResponseEntity<InfoResDto> getInfo(String videoUrl) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(remoteServiceKey);
        HttpEntity<?> entity = new HttpEntity<>(headers);
       return restTemplate.exchange(
                remoteServiceHost + "/info?video_url=" +videoUrl,
                HttpMethod.GET,
                entity,
                InfoResDto.class
        );
    }

    @Override
    public ResponseEntity<StoreVidInfoDto> getVideo(Integer quality, String videoUrl) {

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(remoteServiceKey);
        HttpEntity<?> entity = new HttpEntity<>(headers);
        return restTemplate.exchange(
                remoteServiceHost + "/forward/video?video_url=" +videoUrl+"&quality="+quality,
                HttpMethod.GET,
                entity,
                StoreVidInfoDto.class
        );
    }

    @Override
    public ResponseEntity<StoreVidInfoDto> getAudio(String videoUrl) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(remoteServiceKey);
        HttpEntity<?> entity = new HttpEntity<>(headers);
        return restTemplate.exchange(
                remoteServiceHost + "/forward/audio?video_url=" +videoUrl,
                HttpMethod.GET,
                entity,
                StoreVidInfoDto.class
        );
    }

    @Override
    public void setTelegramWebhook() {
        String deleteTelegramWebhook = restTemplate.getForObject("https://api.telegram.org/bot" + botToken + "/deleteWebhook" ,String.class);
        System.out.println(deleteTelegramWebhook);
        String setTelegramWebhook = restTemplate.getForObject("https://api.telegram.org/bot" + botToken + "/setWebhook?url=" + telegramBotWebhook, String.class);
        System.out.println(setTelegramWebhook);
        if (setTelegramWebhook == null) throw new RuntimeException("Webhook not set");
        if (!setTelegramWebhook.contains("\"result\":true")) throw new RuntimeException("Webhook not set");

    }

    @Override
    public void setStVidServerWebhook() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(remoteServiceKey);
        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<String> setExchange = restTemplate.exchange(
                remoteServiceHost + "/user/set-webhook?webhook=" + stVidWebhook,
                HttpMethod.GET,
                entity,
                String.class);
        if (setExchange.getStatusCode().is2xxSuccessful() && setExchange.hasBody() && setExchange.getBody()!=null){
            System.out.println(setExchange.getBody());
            return;
        }
        throw new RuntimeException("Webhook not set");
    }
}
