package sfera.crmsfera.entity;



import sfera.crmsfera.entity.enums.ResultStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;
import sfera.crmsfera.entity.base.BaseEntity;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Where(clause = "active = true")
public class Result extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    private  Category category;

    private Integer questionCount;

    private Integer totalScore;

    // Tuplangan ball
    private Integer earnedScore;

    // Natija foizda
    private Double percentage;

    // PASSED / FAILED
    @Enumerated(EnumType.STRING)
    private ResultStatus status;

    private Integer attemptNumber;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private boolean retakePermission=false;

    boolean deleted=false;


}
