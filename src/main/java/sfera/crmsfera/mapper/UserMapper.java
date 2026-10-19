package sfera.crmsfera.mapper;

import sfera.crmsfera.dto.response.ResGroupDTO;
import sfera.crmsfera.dto.response.ResStudent;
import sfera.crmsfera.dto.response.ResTeacher;
import sfera.crmsfera.dto.response.UserResponse;
import sfera.crmsfera.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserMapper {

    public UserResponse toResponseUser(User user) {
        return UserResponse.builder()
            .id(user.getId())
            .fullName(user.getFullName())
            .phone(user.getPhone())
            .imgUrl(user.getImageUrl())
            .role(user.getRole().name())
            .build();
    }

//    public UserDTO toUserDTO(User user) {
//    return UserDTO.builder()
//            .id(user.getId())
//            .fullName(user.getFullName())
//            .phone(user.getPhone())
//            .imageUrl(user.getImageUrl())
//            .build();
//    }

    public ResTeacher resTeacher(User teacher, List<ResStudent> studentList, List<ResGroupDTO> groupList) {
        return ResTeacher.builder()
                .id(teacher.getId())
                .fullName(teacher.getFullName())
                .phone(teacher.getPhone())
                .imageUrl(teacher.getImageUrl())
                .studentList(studentList)
                .groupList(groupList)
                .build();
    }
}
