package storevid.dao;

import lombok.*;
import storevid.enums.RequestState;

import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class StoreVidInfoDto {
    private UUID requestSsid;
    private Long queueNumber;
    private RequestState state;
}
