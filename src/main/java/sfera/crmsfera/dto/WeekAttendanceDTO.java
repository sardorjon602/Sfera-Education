package sfera.crmsfera.dto;



import sfera.crmsfera.entity.enums.WeekDays;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class WeekAttendanceDTO {
    private WeekDays day;     // MONDAY...
    private boolean present; // true/false
}