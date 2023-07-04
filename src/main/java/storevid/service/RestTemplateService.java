package storevid.service;

import org.springframework.http.ResponseEntity;
import storevid.dao.InfoResDto;
import storevid.dao.StoreVidInfoDto;

public interface RestTemplateService {

    ResponseEntity<InfoResDto> getInfo(String videoUrl);

    ResponseEntity<StoreVidInfoDto> getVideo(Integer quality, String url);

    ResponseEntity<StoreVidInfoDto> getAudio(String url);

    void setTelegramWebhook();
    void setStVidServerWebhook();
}
