package dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReqGroupNotif {

    private Long groupId;

    private String title;
    private String description;
}
