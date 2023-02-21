package storevid.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "referal")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Referal {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

    @OneToOne
    private User invitor;

    @OneToMany(fetch = FetchType.LAZY)
    private List<User> invited;

    private Integer invitedCount = invited.size();
}
