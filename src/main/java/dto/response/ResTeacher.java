package dto.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResTeacher {
    private Long id;
    private String fullName;
    private String phone;
    private String imageUrl;
    private List<ResStudent> studentList;
    private List<ResGroupDTO> groupList;
}
