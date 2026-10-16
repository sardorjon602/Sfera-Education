package dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReqCategoryDTO {
    private Long id;

    private String name;

    private String description;

    private int duration;

    private int questionLimit;

    private String imgUrl;

}
