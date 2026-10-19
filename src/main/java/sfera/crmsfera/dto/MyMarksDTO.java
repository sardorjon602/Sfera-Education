package sfera.crmsfera.dto;

import sfera.crmsfera.entity.enums.MarkCategoryStatus;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class MyMarksDTO {

    private Long groupId;
    private String groupName;
    private String month; // "2026-02"
    private Double marks;
    private MarkCategoryStatus status;

}
