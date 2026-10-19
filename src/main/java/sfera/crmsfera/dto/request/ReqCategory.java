package sfera.crmsfera.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReqCategory {

    private String name;

    private String description;

    private int duration;

    private int questionLimit;

    private String imgUrl;

}
