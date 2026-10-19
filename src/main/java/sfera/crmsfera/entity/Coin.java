package sfera.crmsfera.entity;

import jakarta.persistence.Entity;
import lombok.*;
import sfera.crmsfera.entity.base.BaseEntity;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Coin extends BaseEntity {
    private String name;
    private String description;
    private String imgUrl;
    private int coin;
}
