package sfera.crmsfera.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReqNotification {

    private String message;
    private String description;
    private Long studentId;
    private Long parentId;
}
