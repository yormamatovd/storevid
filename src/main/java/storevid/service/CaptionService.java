package storevid.service;

import storevid.dao.AvailableFormatDto;
import storevid.dao.InfoResDto;
import storevid.enums.RequestState;

import java.util.List;

public interface CaptionService {
    String infoCaptionMaker(InfoResDto info, List<AvailableFormatDto> sortedVideoFormats);
    String videoProcessCaptionMaker(Integer quality, String size, Long queue, String videoName, String videoUrl, RequestState requestState);

    String photoCompletedMaker(String videoName, String url);

    String audioCompletedMaker(String videoName, String url);
    String audioProcessCaptionMaker( String size, Long queue, String videoName, String videoUrl,RequestState requestState);
}
