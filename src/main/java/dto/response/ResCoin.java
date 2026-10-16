package dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResCoin {
    private Long id;
    private String name;
    private String description;
    private String imgUrl;
    private int coin;
}
