package entity;

import jakarta.persistence.Entity;
import lombok.*;
import entity.base.BaseEntity;
import org.hibernate.annotations.Where;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Where(clause = "active = true")
public class Room extends BaseEntity {
    private String name;
}
