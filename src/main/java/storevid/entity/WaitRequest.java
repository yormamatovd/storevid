package storevid.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WaitRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String url;

    private String videoName;

    private boolean moreState = true;

    @Column(unique = true)
    private UUID requestSSID;

    @Column
    private Integer messageId;

    @Column
    private Long chatId;
}
