package storevid.service;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import storevid.dao.AvailableFormatDto;

import java.util.List;

public interface BoardService {

    InlineKeyboardMarkup buildFormatsBoard(List<AvailableFormatDto> formats,AvailableFormatDto audioFormat, Long waitRequestId);

    InlineKeyboardMarkup buildLangBoard();

}
