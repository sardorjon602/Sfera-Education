package sfera.crmsfera.mapper;

import sfera.crmsfera.dto.AttendanceDto;
import sfera.crmsfera.entity.Attendance;
import org.springframework.stereotype.Component;

@Component
public class AttendanceMapper {

    public AttendanceDto toDto(Attendance attendance) {
        return AttendanceDto.builder()
                .id(attendance.getId())
                .date(attendance.getDate())
                .description(attendance.getDescription())
                .fullName(attendance.getStudent().getFullName())
                .status(attendance.getStatus())
                .studentId(attendance.getStudent().getId())
                .build();
    }
}
