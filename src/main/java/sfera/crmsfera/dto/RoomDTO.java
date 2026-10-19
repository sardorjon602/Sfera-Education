package sfera.crmsfera.dto;

import sfera.crmsfera.dto.request.ReqGroupDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoomDTO {
    @Schema(hidden = true)
    private Long id;

    private String name;

    @Schema(hidden = true)
    private ReqGroupDTO schedules;
}
