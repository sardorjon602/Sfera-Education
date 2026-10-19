package sfera.crmsfera.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ScheduleResponseDTO {
    private String roomName;
    private List<GroupScheduleDTO> groups;
}

