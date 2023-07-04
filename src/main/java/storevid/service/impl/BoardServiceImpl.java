package storevid.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import storevid.dao.AvailableFormatDto;
import storevid.service.BoardService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
    @Override
    public InlineKeyboardMarkup buildFormatsBoard(List<AvailableFormatDto> sortedVideoFormats, AvailableFormatDto audioFormat, Long waitRequestId) {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        List<InlineKeyboardButton> row = new ArrayList<>();

        for (AvailableFormatDto videoFormat : sortedVideoFormats) {
            row.add(new InlineKeyboardButton(
                    "\uD83D\uDCF9 " + videoFormat.getResolution().getHeight() + "p", null,
                    "video-" + waitRequestId + "-" +
                            videoFormat.getResolution().getHeight() + "-" +
                            (videoFormat.getFilesize() > 1_073_741_824 ?
                                    "<b>" + (videoFormat.getFilesize() / 1_073_741_824) + "</b>GB"
                                    : "<b>" + (videoFormat.getFilesize() / 1_048_576) + "</b>MB"),
                    null, null,
                    null, false, null, null
            ));
            if (row.size() == 3) {
                keyboard.add(List.copyOf(row));
                row.clear();
            }
        }


        List<InlineKeyboardButton> lastRow = new ArrayList<>();
        InlineKeyboardButton mp3Btn = new InlineKeyboardButton();
        mp3Btn.setText("\uD83D\uDD09 MP3");
        mp3Btn.setCallbackData("audio-" + waitRequestId + "-" + (audioFormat.getFilesize() > 1_073_741_824 ?
                "<b>" + (audioFormat.getFilesize() / 1_073_741_824) + "</b>GB"
                : "<b>" + (audioFormat.getFilesize() / 1_048_576) + "</b>MB"));
        InlineKeyboardButton photoBtn = new InlineKeyboardButton();
        photoBtn.setText("\uD83C\uDFDE");
        photoBtn.setCallbackData("photo-" + waitRequestId);
        InlineKeyboardButton moreBtn = new InlineKeyboardButton();
        moreBtn.setText("more ⤵️");
        moreBtn.setCallbackData("more-" + waitRequestId);

        lastRow.add(mp3Btn);
        lastRow.add(photoBtn);
        if (sortedVideoFormats.size() <= 3) {
            lastRow.add(moreBtn);
        }
        keyboard.add(lastRow);
        markup.setKeyboard(keyboard);
        return markup;
    }

    @Override
    public InlineKeyboardMarkup buildLangBoard() {
        InlineKeyboardButton enBtn = new InlineKeyboardButton("English \uD83C\uDDEC\uD83C\uDDE7");
        InlineKeyboardButton ruBtn = new InlineKeyboardButton("Russian \uD83C\uDDF7\uD83C\uDDFA");
        InlineKeyboardButton uzBtn = new InlineKeyboardButton("Uzbek \uD83C\uDDFA\uD83C\uDDFF");
        enBtn.setCallbackData("en");
        ruBtn.setCallbackData("ru");
        uzBtn.setCallbackData("uz");
        return new InlineKeyboardMarkup(List.of(List.of(enBtn),List.of(ruBtn),List.of(uzBtn)));
    }
}

