package dto.request;

import entity.enums.AttendaceEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReqAttendance {

    private LocalDate date;

    private Long studentId;

    private AttendaceEnum status;
}
