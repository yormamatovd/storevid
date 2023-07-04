package storevid.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import storevid.entity.main.AbsMain;
import storevid.helper.Time;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User extends AbsMain {

    @Column(nullable = false,unique = true)
    private Long chatId;

    private String username;

    @Column(nullable = false)
    private String firstname;

    private String lastname;

    @Column(nullable = false, updatable = false)
    private Long startedTime;

    @Column(nullable = false)
    private Long lastConnection;

    @PrePersist
    private void insert() {
        this.startedTime = Time.getCurrentUTCSeconds();
        this.lastConnection = Time.getCurrentUTCSeconds();
    }

    @PreUpdate
    private void update(){
        this.lastConnection = Time.getCurrentUTCSeconds();
    }
}
