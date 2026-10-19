package sfera.crmsfera.dto.request;


import com.fasterxml.jackson.annotation.JsonFormat;
import sfera.crmsfera.entity.enums.GroupEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReqGroupDTO {

    private Long id;

    private String name;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private String startTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private String endTime;

    private List<GroupEnum> weekDays;

    private Long teacherId;

    private Long categoryId;

    private Long roomId;

    @Schema(hidden = true)
    private String roomName;

    @Schema(hidden = true)
    private String teacherName;

    @Schema(hidden = true)
    private String categoryName;
}
