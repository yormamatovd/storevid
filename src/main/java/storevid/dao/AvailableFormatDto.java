package storevid.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import storevid.enums.AvailableState;
import storevid.enums.ExtName;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AvailableFormatDto {

    private ExtName ext;
    private ResolutionDto resolution;
    private Long filesize;
    private AvailableState state;

}
