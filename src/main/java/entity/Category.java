package entity;


import jakarta.persistence.Entity;
import lombok.*;
import org.hibernate.annotations.Where;
import entity.base.BaseEntity;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Where(clause = "active = true")
public class Category  extends BaseEntity {

    private String name;

    private String description;

    private Integer duration;

    private int questionLimit;

    private String imgUrl;

}
