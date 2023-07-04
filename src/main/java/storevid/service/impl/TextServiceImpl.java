package storevid.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.CopyMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import storevid.config.Msg;
import storevid.config.Session;
import storevid.config.SessionUser;
import storevid.dao.AvailableFormatDto;
import storevid.dao.InfoResDto;
import storevid.entity.WaitRequest;
import storevid.enums.AvailableState;
import storevid.enums.ExtName;
import storevid.helper.LinkChecker;
import storevid.repo.WaitRequestRepo;
import storevid.service.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TextServiceImpl implements TextService {

    private final RestTemplateService restTemplateService;
    private final SenderService sender;
    private final BoardService boardService;
    private final WaitRequestRepo waitRequestRepo;
    private final CaptionService captionService;

    @Override
    public void text() {
        if (Session.text.equals("/start")) {
            startMessage();
            boardService.buildLangBoard();

        } else if (LinkChecker.isValid(Session.text)) {
            fetchVideoData();
        } else {
            sender.sendMessage(new SendMessage(SessionUser.getChatIdString(), Msg.get("WRONG_URL")));
        }
    }

    private void startMessage() {
        SendMessage sendMessage=new SendMessage();
        sendMessage.setChatId(SessionUser.getChatIdString());
        sendMessage.setText(Msg.get("CHOOSE_LANG"));
        sendMessage.setReplyMarkup(boardService.buildLangBoard());
        sender.sendMessage(sendMessage);
    }

    private void fetchVideoData() {
        ResponseEntity<InfoResDto> infoResponse = restTemplateService.getInfo(Session.text);
        if (infoResponse.getStatusCode().is2xxSuccessful() && infoResponse.hasBody() && infoResponse.getBody() != null) {
            InfoResDto info = infoResponse.getBody();
            WaitRequest waitRequest = new WaitRequest();
            waitRequest.setUrl(Session.text);
            waitRequest.setMoreState(true);
            waitRequest.setVideoName(info.getVideoName());
            waitRequest.setUrl(Session.text);
            waitRequestRepo.save(waitRequest);
            CopyMessage copyMessage = buildCopyMessage(waitRequest, info, List.of(360, 480, 720));
            sender.copyMessage(copyMessage);
        }
    }

    private CopyMessage buildCopyMessage(WaitRequest waitRequest,
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

        CopyMessage copyMessage = new CopyMessage();
        copyMessage.setFromChatId(info.getThumbnailLocationChannelUsername());
        copyMessage.setMessageId(info.getThumbnailLocationMessageId());
        copyMessage.setChatId(SessionUser.chatId);
        copyMessage.setCaption(
                captionService.infoCaptionMaker(info, sortedVideoFormats)
        );
        copyMessage.enableHtml(true);

        copyMessage.setReplyMarkup(
                boardService.buildFormatsBoard(
                        sortedVideoFormats,
                        info.getFormats().stream().filter(dto -> dto.getExt() == ExtName.MP3).findFirst().orElse(new AvailableFormatDto(ExtName.MP3, null, 0L, AvailableState.LARGE_SIZE)),
                        waitRequest.getId()
                )
        );
        return copyMessage;
    }


}
