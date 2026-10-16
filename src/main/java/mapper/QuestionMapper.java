package mapper;

import dto.response.ResOption;
import dto.response.ResQuestion;
import entity.Option;
import entity.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class QuestionMapper {

//    public ResQuestion toResponse(Question question) {
//
//        List<ResOption> options = question.getOptions().stream()
//                .map(o -> new ResOption(
//                        o.getId(),
//                        o.getText(),
//                        o.getFile()
//                ))
//                .toList();
//
//        return new ResQuestion(
//                question.getId(),
//                question.getText(),
//                question.getDifficulty(),
//                question.getScore(),
//                question.getFile(),
//                options
//        );
//    }

    public ResQuestion toQuestionResponse(Question question) {

        List<Option> options = question.getOptions().stream()
                .filter(o -> !o.isDeleted())
                .collect(Collectors.toList());

        Collections.shuffle(options);

        List<ResOption> resOptions = options.stream()
                .map(o -> new ResOption(o.getId(), o.getText(), o.getFile()))
                .toList();

        return new ResQuestion(
                question.getId(),
                question.getText(),
                question.getDifficulty(),
                question.getScore(),
                question.getFile(),
                resOptions);
    }

}
