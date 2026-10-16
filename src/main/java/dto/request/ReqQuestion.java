package dto.request;

import entity.enums.QuestionDifficulty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ReqQuestion {

    private String text;
    private QuestionDifficulty difficulty;
    private List<ReqOption> options;
    private Integer score;
    private String file;
    private Long categoryId;

}
