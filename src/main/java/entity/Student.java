package entity;



import entity.base.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
public class Student extends BaseEntity {

    private String fullName;

    private String phone;

    private String password;

    private String imgUrl;

    private long telegramId;

    private int coin;

    @ManyToOne
    private User parent;

    @ManyToOne
    private Group group;

}
