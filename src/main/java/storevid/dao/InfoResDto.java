package storevid.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InfoResDto {

    private String channelUrl;
    private String channelName;
    private String videoName;
    private String videoUrl;
    private String duration;
    private String thumbnailLocationChannelUsername;
    private Integer thumbnailLocationMessageId;
    private List<AvailableFormatDto> formats=new ArrayList<>();

}
