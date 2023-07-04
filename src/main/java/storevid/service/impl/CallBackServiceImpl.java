package storevid.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendAudio;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageCaption;
import storevid.config.Session;
import storevid.config.SessionUser;
import storevid.dao.AvailableFormatDto;
import storevid.dao.InfoResDto;
import storevid.dao.StoreVidInfoDto;
import storevid.entity.WaitRequest;
import storevid.enums.AvailableState;
import storevid.enums.ExtName;
import storevid.enums.RequestState;
import storevid.repo.WaitRequestRepo;
import storevid.service.*;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CallBackServiceImpl implements CallBackService {

    private final RestTemplateService restTemplateService;
    private final WaitRequestRepo waitRequestRepo;
    private final BoardService boardService;
    private final CaptionService captionService;
    private final SenderService senderService;


    @Override
    public void callback() {
        if (Session.callbackData.startsWith("more-")) {
            moreBtnClicked();
        } else if (Session.callbackData.startsWith("video-")) {
            videoFormatClicked();
        } else if (Session.callbackData.startsWith("audio-")) {
            audioFormatClicked();
        } else if (Session.callbackData.startsWith("photo-")) {
            photoFormatClicked();
        }
    }

    private void videoFormatClicked() {
        String[] split = Session.callbackData.replaceAll("video-", "").trim().split("-");
        Long waitRequestId = Long.valueOf(split[0]);
        Integer quality = Integer.valueOf(split[1]);
        String size = split[2];

        Optional<WaitRequest> waitRequestOptional = waitRequestRepo.findById(waitRequestId);
        if (waitRequestOptional.isPresent()) {
            WaitRequest waitRequest = waitRequestOptional.get();
            ResponseEntity<StoreVidInfoDto> response = restTemplateService.getVideo(quality, waitRequest.getUrl());
            if (response.getStatusCode().is2xxSuccessful() && response.hasBody() && response.getBody() != null) {
                StoreVidInfoDto body = response.getBody();
                waitRequest.setRequestSSID(body.getRequestSsid());
                waitRequest.setMoreState(false);
                waitRequest.setUrl(null);
                waitRequest.setMessageId(Session.messageId);
                waitRequest.setChatId(SessionUser.chatId);
                waitRequestRepo.save(waitRequest);
                String caption = captionService.videoProcessCaptionMaker(quality, size, body.getQueueNumber(), waitRequest.getVideoName(), waitRequest.getUrl(), RequestState.ACCEPTED);
                EditMessageCaption editMessageCaption = buildEditMessageCaptionRemoveKeyboard(caption, Session.messageId, SessionUser.chatId);
                senderService.editMessageCaption(editMessageCaption);
            }
        }
    }

    private void photoFormatClicked() {
        Long waitRequestId = Long.valueOf(Session.callbackData.replaceAll("photo-", "").trim());

        Optional<WaitRequest> waitRequestOptional = waitRequestRepo.findById(waitRequestId);
        if (waitRequestOptional.isPresent()) {
            WaitRequest waitRequest = waitRequestOptional.get();
            String caption = captionService.photoCompletedMaker(waitRequest.getVideoName(), waitRequest.getUrl());
            EditMessageCaption editMessageCaption = buildEditMessageCaptionRemoveKeyboard(caption, Session.messageId, SessionUser.chatId);
            waitRequestRepo.delete(waitRequest);
            senderService.editMessageCaption(editMessageCaption);
        }
    }

    private void audioFormatClicked() {
        String[] split = Session.callbackData.replaceAll("audio-", "").trim().split("-");
        Long waitRequestId = Long.valueOf(split[0]);
        String size = split[1];
        Optional<WaitRequest> waitRequestOptional = waitRequestRepo.findById(waitRequestId);
        if (waitRequestOptional.isPresent()) {
            WaitRequest waitRequest = waitRequestOptional.get();
            waitRequest.setMessageId(Session.messageId);
            waitRequest.setChatId(SessionUser.chatId);
            waitRequestRepo.save(waitRequest);
            ResponseEntity<StoreVidInfoDto> response = restTemplateService.getAudio(waitRequest.getUrl());
            if (response.getStatusCode().is2xxSuccessful() && response.hasBody() && response.getBody() != null) {
                StoreVidInfoDto body = response.getBody();
                String caption = captionService.audioProcessCaptionMaker(size, body.getQueueNumber(), waitRequest.getVideoName(), waitRequest.getUrl(), RequestState.ACCEPTED);
                EditMessageCaption editMessageCaption = buildEditMessageCaptionRemoveKeyboard(caption, Session.messageId, SessionUser.chatId);
                waitRequest.setRequestSSID(body.getRequestSsid());
                waitRequestRepo.save(waitRequest);
                senderService.editMessageCaption(editMessageCaption);
            }
        }
    }


    private void moreBtnClicked() {
        Long waitRequestId = Long.valueOf(Session.callbackData.replaceAll("more-", "").trim());
        Optional<WaitRequest> waitRequestOptional = waitRequestRepo.findById(waitRequestId);
        if (waitRequestOptional.isPresent()) {
            WaitRequest waitRequest = waitRequestOptional.get();
            if (waitRequest.isMoreState()) {
                ResponseEntity<InfoResDto> response = restTemplateService.getInfo(waitRequest.getUrl());
                if (response.getStatusCode().is2xxSuccessful() && response.hasBody() && response.getBody() != null) {
                    InfoResDto info = response.getBody();
                    EditMessageCaption editMessageCaption = buildEditMessageCaption(waitRequest, info, List.of(144, 240, 360, 480, 720, 1080));
                    senderService.editMessageCaption(editMessageCaption);
                    waitRequest.setMoreState(false);
                    waitRequestRepo.save(waitRequest);
                }
            }
        }
    }

    private EditMessageCaption buildEditMessageCaption(WaitRequest waitRequest,
                                                       InfoResDto info,
                                                       List<Integer> formats) {
        List<AvailableFormatDto> sortedVideoFormats =
                info.getFormats().stream().filter(dto -> dto.getExt() == ExtName.MP4)
                        .sorted(Comparator.comparing(dto -> dto.getResolution().getHeight()))
                        .collect(Collectors.toList());
        sortedVideoFormats.removeIf(dto -> {
            if (dto.getExt() == ExtName.MP4) {
                return !formats.contains(dto.getResolution().getHeight());
            }
            return true;
        });

        EditMessageCaption editMessageCaption = new EditMessageCaption();
        editMessageCaption.setMessageId(Session.messageId);
        editMessageCaption.setChatId(SessionUser.chatId);
        editMessageCaption.setCaption(
                captionService.infoCaptionMaker(info, sortedVideoFormats)
        );
        editMessageCaption.setParseMode(ParseMode.HTML);
        editMessageCaption.setReplyMarkup(
                boardService.buildFormatsBoard(
                        sortedVideoFormats,
                        info.getFormats().stream().filter(dto -> dto.getExt() == ExtName.MP3).findFirst().orElse(new AvailableFormatDto(ExtName.MP3, null, 0L, AvailableState.LARGE_SIZE)),
                        waitRequest.getId()
                ));


        return editMessageCaption;
    }

    private EditMessageCaption buildEditMessageCaptionRemoveKeyboard(String caption, Integer messageId, Long chatId) {
        EditMessageCaption editMessageCaption = new EditMessageCaption();
        editMessageCaption.setReplyMarkup(null);
        editMessageCaption.setCaption(caption);
        editMessageCaption.setParseMode(ParseMode.HTML);
        editMessageCaption.setChatId(chatId);
        editMessageCaption.setMessageId(messageId);
        return editMessageCaption;
    }

    private SendAudio buildAudioMessage(String caption, Long chatId) {
        SendAudio sendAudio = new SendAudio();
        sendAudio.setReplyMarkup(null);
        sendAudio.setCaption(caption);
        sendAudio.setParseMode(ParseMode.HTML);
        sendAudio.setChatId(chatId);
        return sendAudio;
    }

}
