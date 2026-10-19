package sfera.crmsfera.dto.request;

import sfera.crmsfera.dto.AnswerDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ReqResult {
    private Long resultId;
    private List<AnswerDTO> answers;
}
