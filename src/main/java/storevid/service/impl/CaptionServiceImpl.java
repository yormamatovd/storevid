package storevid.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import storevid.config.Msg;
import storevid.dao.AvailableFormatDto;
import storevid.dao.InfoResDto;
import storevid.enums.AvailableState;
import storevid.enums.RequestState;
import storevid.service.CaptionService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CaptionServiceImpl implements CaptionService {
    @Override
    public String infoCaptionMaker(InfoResDto info, List<AvailableFormatDto> sortedVideoFormats) {
        StringBuilder caption = new StringBuilder();
        caption.append("\uD83D\uDCF9 ");
        caption.append(info.getVideoName());
        caption.append("<a href='").append(info.getVideoUrl()).append("'> --> </a>\n");
        caption.append("\uD83D\uDCE2 ");
        caption.append("<a href='").append(info.getChannelUrl())
                .append("'>").append(info.getChannelName()).append("</a>\n\n");


        sortedVideoFormats.forEach(dto ->
                caption.append("<code>")
                        .append(
                                String.format("%s %7s %15s",
                                        dto.getState() == AvailableState.READY ? "\uD83D\uDE80" : "✅",
                                        dto.getResolution().getHeight() + "p:",
                                        dto.getFilesize() > 1_073_741_824 ?
                                                "<b>" + (dto.getFilesize() / 1_073_741_824) + "</b>GB"
                                                : "<b>" + (dto.getFilesize() / 1_048_576) + "</b>MB"
                                )
                        )
                        .append("</code>\n")
        );

        caption.append("\n").append(Msg.get("DOWN_FORMATS"));
        return caption.toString();
    }

    @Override
    public String videoProcessCaptionMaker(Integer quality, String size, Long queue, String videoName, String videoUrl, RequestState requestState) {
        StringBuilder builder = new StringBuilder();
        builder.append("\uD83D\uDCF9 ");
        builder.append(videoName);
        builder.append("<a href='").append(videoUrl).append("'> --> </a>\n");
        builder.append("\uD83C\uDFAC ").append(quality).append("p").append(" \uD83D\uDCBE ").append(size).append("\n\n");

        if (requestState == RequestState.ACCEPTED) {
            builder.append("\uD83D\uDD53 ").append(Msg.get("IN_QUEUE")).append(" ").append(queue).append("\n");
        } else if (requestState == RequestState.DOWNLOADING) {
            builder.append("\uD83D\uDCE5 ").append(Msg.get("DOWNLOADING")).append("\n");
        } else if (requestState == RequestState.COMPRESSING) {
            builder.append("\uD83D\uDEE0 ").append(Msg.get("COMPRESSING")).append("\n");
        } else {
            builder.append("\uD83D\uDCE4 ").append(Msg.get("UPLOADING")).append("\n");
        }

        builder.append("□□□□□□□□□□ ").append("0%");
        return builder.toString();
    }

    @Override
    public String audioProcessCaptionMaker(String size, Long queue, String videoName, String videoUrl, RequestState requestState) {
        StringBuilder builder = new StringBuilder();
        builder.append("\uD83D\uDD09 ");
        builder.append(videoName);
        builder.append("<a href='").append(videoUrl).append("'> --> </a>\n");
        builder.append(" \uD83D\uDCBE ").append(size).append("\n\n");

        if (requestState == RequestState.ACCEPTED) {
            builder.append("\uD83D\uDD53 ").append(Msg.get("IN_QUEUE")).append(" ").append(queue).append("\n");
        } else if (requestState == RequestState.DOWNLOADING) {
            builder.append("\uD83D\uDCE5 ").append(Msg.get("DOWNLOADING")).append("\n");
        } else {
            builder.append("\uD83D\uDCE4 ").append(Msg.get("UPLOADING")).append("\n");
        }

        builder.append("\n");
        builder.append("□□□□□□□□□□ ").append("0%");
        return builder.toString();
    }

    @Override
    public String photoCompletedMaker(String videoName, String videoUrl) {
        return "\uD83C\uDFDE " + videoName + "<a href='" + videoUrl + "'> --> </a>\n\n" + "@storevid_bot";
    }

    @Override
    public String audioCompletedMaker(String videoName, String videoUrl) {
        return "\uD83D\uDD09 " + videoName + "<a href='" + videoUrl + "'> --> </a>\n\n" + "@storevid_bot";
    }
}
