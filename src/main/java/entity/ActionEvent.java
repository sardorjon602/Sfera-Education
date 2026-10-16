package entity;

import entity.enums.ActionType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import entity.base.BaseEntity;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class ActionEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ActionType actionType;

    private String description;

    private Long userId;

    private String userRole;

    private String entityName;

    private Long entityId;

    private LocalDateTime createdAt;
}
