package sfera.crmsfera.dto;


import sfera.crmsfera.entity.enums.MarkCategoryStatus;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class DayMarkDTO {
    private String dayName;
    private String subjectName;
    private Integer score;
    private MarkCategoryStatus category;
}