package sfera.crmsfera.dto.response;

import sfera.crmsfera.dto.DayStat;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResRoom {
    private Long id;

    private String name;

    private List<ResGroupDTO> schedules;

    private List<DayStat> weeklyStats;
}
