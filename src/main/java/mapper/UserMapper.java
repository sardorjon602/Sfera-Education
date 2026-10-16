package mapper;

import dto.response.ResGroupDTO;
import dto.response.ResStudent;
import dto.response.ResTeacher;
import dto.response.UserResponse;
import entity.User;
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
