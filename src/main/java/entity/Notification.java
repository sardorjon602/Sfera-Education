package entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;
import entity.base.BaseEntity;
import org.hibernate.annotations.Where;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Where(clause = "active = true")
public class Notification extends BaseEntity {

    private String message;
    private String description;
    @ManyToOne
    private Student student;
    @ManyToOne
    private User parent;
    private boolean isRead;

}
