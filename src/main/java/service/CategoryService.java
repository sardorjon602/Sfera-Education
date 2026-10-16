package service;

import dto.ApiResponse;
import dto.request.ReqCategory;
import dto.request.ReqCategoryDTO;
import dto.response.ResCategory;
import dto.response.ResOption;
import dto.response.ResPageable;
import dto.response.ResponseQuestion;
import entity.Category;
import entity.Question;
import entity.Result;
import entity.Student;
import entity.enums.ResultStatus;
import exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import mapper.CategoryMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import repository.CategoryRepository;
import repository.GroupRepository;
import repository.QuestionRepository;
import repository.ResultRepository;
import security.CustomUserDetails;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;
    private final ResultRepository resultRepository;
    private final QuestionRepository questionRepository;
    private final GroupRepository groupRepository;

    public ApiResponse<String> saveCategory(ReqCategory reqCategory) {

        Category category = Category.builder()
                .name(reqCategory.getName())
                .description(reqCategory.getDescription())
                .duration(reqCategory.getDuration())
                .imgUrl(reqCategory.getImgUrl())
                .questionLimit(reqCategory.getQuestionLimit())
                .build();

        categoryRepository.save(category);
        return ApiResponse.success(null, "Category successfully saved");
    }


    public ApiResponse<String> updateCategory(ReqCategoryDTO reqCategoryDTO) {
        Category category = categoryRepository.findById(reqCategoryDTO.getId()).orElseThrow(
                () -> new DataNotFoundException("Category not found")
        );

        category.setName(reqCategoryDTO.getName());
        category.setDescription(reqCategoryDTO.getDescription());
        category.setDuration(reqCategoryDTO.getDuration());
        category.setImgUrl(reqCategoryDTO.getImgUrl());
        category.setQuestionLimit(reqCategoryDTO.getQuestionLimit());

        categoryRepository.save(category);

        return ApiResponse.success(null, "Category successfully updated");
    }


    public ApiResponse<String> deleteCategory(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException("Category not found")
        );

        int size = questionRepository.findAllByCategoryIdAndDeletedFalse(category.getId()).size();
        int size1 = groupRepository.findAllByCategoryIdAndActiveTrue(category.getId()).size();
        if (size1 == 0 && size == 0) {
            category.setActive(false);
        } else {
            return ApiResponse.error("Cannot delete category");
        }

        categoryRepository.save(category);
        return ApiResponse.success(null, "Category successfully deleted");
    }


    public ApiResponse<ResCategory> getCategoryById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException("Category not found")
        );

        return ApiResponse.success(categoryMapper.toRes(category), "Success");
    }


    public ApiResponse<List<ResCategory>> getAllCategory() {
        List<Category> list = categoryRepository.findAll();

        if (list.isEmpty()) {
            return ApiResponse.error("Category not found");
        }

        List<ResCategory> res = list.stream()
                .map(categoryMapper::toRes)
                .toList();

        return ApiResponse.success(res, "Success");
    }


    public ApiResponse<ResPageable> searchCategory(String name, int page, int size) {
        Page<Category> categoryPage = categoryRepository.searchCategory(name, PageRequest.of(page, size));

        if (categoryPage.getTotalElements() == 0) {
            return ApiResponse.error("Category not found");
        }

        List<ResCategory> categoryList = categoryPage.getContent().stream().map(categoryMapper::toRes).toList();
        ResPageable resPageable = ResPageable.builder()
                .page(page)
                .size(size)
                .totalElements(categoryPage.getTotalElements())
                .totalElements(categoryPage.getTotalPages())
                .body(categoryList)
                .build();
        return ApiResponse.success(resPageable, "Success");
    }


    public ApiResponse<List<ResponseQuestion>> startTest(CustomUserDetails currentUser, Long categoryId) {

        if (!currentUser.isStudent()) {
            return ApiResponse.error("Only students can start tests");
        }

        Student student = currentUser.getStudent();

        Category category = categoryRepository.findById(categoryId).orElseThrow(
                () -> new DataNotFoundException("Category not found"));

        int limit = category.getQuestionLimit();

        Optional<Result> lastResult =
                resultRepository.findTopByStudentIdAndCategoryIdOrderByAttemptNumberDesc(student.getId(),categoryId);
        if (lastResult.isPresent()) {
            Result r = lastResult.get();

            if (r.getStatus() == ResultStatus.PASSED) {
                return ApiResponse.error("You already passed this quiz");
            }

            if (!r.isRetakePermission()) {
                return ApiResponse.error("Retake not allowed yet");
            }
        }

        int attemptNumber = resultRepository.countByStudentIdAndCategoryId(student.getId(),categoryId) + 1;

        Result result = Result.builder()
                .student(student)
                .category(category)
                .status(ResultStatus.STARTED)
                .attemptNumber(attemptNumber)
                .startTime(LocalDateTime.now())
                .retakePermission(false)
                .questionCount(limit)
                .build();
        Result save = resultRepository.save(result);

        List<Question> allQuestions = questionRepository.findAllByCategoryIdAndDeletedFalse(categoryId);
        if (allQuestions.size() < category.getQuestionLimit()) {
            return ApiResponse.error("Savollar Kategoriya buyicha yetarli emas, savol qo‘shing.");
        }

        if (allQuestions.isEmpty()) {
            throw new DataNotFoundException("No questions found");
        }

        Collections.shuffle(allQuestions);

        List<ResponseQuestion> responseQuestions = allQuestions.stream().map(question -> {
            List<ResOption> options = question.getOptions().stream().map(option -> ResOption.builder()
                    .id(option.getId())
                    .text(option.getText())
                    .file(option.getFile())
                    .build()).collect(Collectors.toList());

            Collections.shuffle(options);

            return ResponseQuestion.builder()
                    .id(question.getId())
                    .resultId(save.getId())
                    .text(question.getText())
                    .difficulty(question.getDifficulty())
                    .categoryId(category.getId())
                    .categoryName(category.getName())
                    .file(question.getFile())
                    .options(options)
                    .build();
        }).collect(Collectors.toList());
        return ApiResponse.success(responseQuestions, "Category successfully started");
    }

}
