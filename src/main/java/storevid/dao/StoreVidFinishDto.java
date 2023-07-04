package storevid.dao;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class StoreVidFinishDto {
   private UUID requestSsid;
   private SentDto sent;
}
