package sfera.crmsfera.mapper;

import sfera.crmsfera.dto.GroupDTO;
import sfera.crmsfera.dto.response.ResGroup;
import sfera.crmsfera.dto.response.ResGroupDTO;
import sfera.crmsfera.entity.Group;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import sfera.crmsfera.repository.StudentRepository;

@Component
@RequiredArgsConstructor
public class GroupMapper {

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    public GroupDTO groupDTO(Group group) {
        return GroupDTO.builder()
                .id(group.getId())
                .name(group.getName())
                .startTime(group.getStartTime().toString())
                .endTime(group.getEndTime().toString())
                .roomName(group.getRoom() != null ? group.getRoom().getName():null)
                .teacherName(group.getTeacher() != null ? group.getTeacher().getFullName():null)
                .roomId(group.getRoom() != null ? group.getRoom().getId():null)
                .teacherId(group.getTeacher() != null ? group.getTeacher().getId() : null)
                .categoryId(group.getCategory() != null ? group.getCategory().getId() : null)
                .categoryName(group.getCategory() != null ? group.getCategory().getName() : null)
                .weekDays(group.getWeekDays().stream().map(Enum::toString).toList())
                .students(studentRepository.findAllByGroup_idAndActiveTrue(group.getId()).stream()
                        .map(studentMapper::toStudentDTO).toList())
                .build();
    }


    public ResGroupDTO toRes(Group group) {
        return ResGroupDTO.builder()
                .id(group.getId())
                .name(group.getName())
                .startTime(group.getStartTime().toString())
                .endTime(group.getEndTime().toString())
                .roomName(group.getRoom() != null ? group.getRoom().getName() : null)
                .teacherName(group.getTeacher() != null ? group.getTeacher().getFullName() : null)
                .roomId(group.getRoom() != null ? group.getRoom().getId() : null)
                .teacherId(group.getTeacher() != null ? group.getTeacher().getId() : null)
                .categoryId(group.getCategory() != null ? group.getCategory().getId() : null)
                .categoryName(group.getCategory() != null ? group.getCategory().getName() : null)
                .weekDays(group.getWeekDays().stream().map(Enum::toString).toList())
                .build();
    }


    public ResGroup toDtoRes(Group group) {
        return ResGroup.builder()
                .id(group.getId())
                .name(group.getName())
                .teacherName(group.getTeacher() != null ? group.getTeacher().getFullName() : null)
                .studentCount(studentRepository.countByGroup_Id(group.getId()))
                .categoryName(group.getCategory().getName())
                .build();
    }
}
