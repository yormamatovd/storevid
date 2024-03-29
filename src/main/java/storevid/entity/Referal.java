package storevid.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import storevid.entity.main.AbsMain;

import javax.persistence.*;

@Entity
@Table(name = "referal")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Referal extends AbsMain {

    @ManyToOne
    private User invitor;

    @ManyToOne
    private User invited;
}
