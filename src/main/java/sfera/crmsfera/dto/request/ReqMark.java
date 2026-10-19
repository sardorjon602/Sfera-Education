package sfera.crmsfera.dto.request;

import sfera.crmsfera.entity.enums.MarkStatus;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReqMark {
    private Long studentId;
    private int homeworkScore;
    private int activityScore;
    private int totalScore;
    private MarkStatus markStatus;
    private LocalDate date;
}
