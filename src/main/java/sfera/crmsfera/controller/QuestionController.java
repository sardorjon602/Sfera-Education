//package sfera.crmsfera.controller;
//
//import sfera.crmsfera.dto.ApiResponse;
//import sfera.crmsfera.dto.ReqQuestionDTO;
//import sfera.crmsfera.dto.request.ReqQuestion;
//import sfera.crmsfera.dto.response.ResPageable;
//import io.swagger.v3.oas.annotations.Operation;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import sfera.crmsfera.service.QuestionService;
//
//@RestController
//@RequestMapping("/question")
//@RequiredArgsConstructor
//public class QuestionController {
//
//    private final QuestionService questionService;
//
//    @PostMapping
//    @Operation(description = "Difficulty -> EASY, MEDIUM, HARD")
//    public ResponseEntity<ApiResponse<String>> addQuestion(@RequestBody ReqQuestion req) {
//        return ResponseEntity.ok(questionService.createQuestion(req));
//    }
//
//    @GetMapping("/{categoryId}")
//    public ResponseEntity<ApiResponse<ResPageable>> getAllQuestionsByCategoryId(@RequestParam(defaultValue = "0") int page,
//                                                                                @RequestParam(defaultValue = "10") int size,
//                                                                                @PathVariable Long categoryId) {
//        return ResponseEntity.ok(questionService.getAllQuestions(categoryId,page,size));
//    }
//
//    @PutMapping
//    public ResponseEntity<ApiResponse<String>> updateQuestion(@RequestBody ReqQuestionDTO req) {
//        return ResponseEntity.ok(questionService.updateQuestion(req));
//    }
//
//    @DeleteMapping("/{questionId}")
//    public ResponseEntity<ApiResponse<String>> deleteQuestion(@PathVariable Long questionId) {
//        return ResponseEntity.ok(questionService.deleteQuestion(questionId));
//    }
//
//
//
//
//}
