package sfera.crmsfera.entity;

import jakarta.persistence.Entity;
import lombok.*;
import org.hibernate.annotations.Where;

import sfera.crmsfera.entity.base.BaseEntity;
import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Where(clause = "active = true")
public class News extends BaseEntity {

    private String name;

    private String description;

    private String imgUrl;

    private LocalDate date;

}
