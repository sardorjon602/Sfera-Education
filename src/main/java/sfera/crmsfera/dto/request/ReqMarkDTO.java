package sfera.crmsfera.dto.request;

import sfera.crmsfera.entity.enums.MarkStatus;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReqMarkDTO {
    private Long id;
    private Long studentId;
    private int homeworkScore;
    private int activityScore;
    private int totalScore;
    private MarkStatus markStatus;
}
