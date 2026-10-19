//package sfera.crmsfera.controller;
//
//import sfera.crmsfera.dto.ApiResponse;
//import sfera.crmsfera.dto.request.ReqResult;
//import sfera.crmsfera.dto.response.ResPageable;
//import sfera.crmsfera.dto.response.ResResult;
//import sfera.crmsfera.dto.response.ResponseQuestion;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.web.bind.annotation.*;
//import sfera.crmsfera.security.CustomUserDetails;
//import sfera.crmsfera.service.CategoryService;
//import sfera.crmsfera.service.ResultService;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/result")
//@RequiredArgsConstructor
//public class ResultController {
//    private final ResultService resultService;
//    private final CategoryService categoryService;
//
//    @PostMapping("/submit-test")
//    public ResponseEntity<ApiResponse<ResResult>> subMitQuestion(@RequestBody ReqResult req){
//        return ResponseEntity.ok(resultService.submitQuiz(req));
//    }
//
//    @GetMapping("/{resultId}")
//    public ResponseEntity<ApiResponse<ResResult>> getResultById(@PathVariable Long resultId){
//        return ResponseEntity.ok(resultService.getResultById(resultId));
//    }
//
//    @GetMapping("/byStudent/{studentId}")
//    public ResponseEntity<ApiResponse<ResPageable>> getResultsByStudent(@PathVariable Long studentId,
//                                                                        @RequestParam(defaultValue = "0") int page,
//                                                                        @RequestParam(defaultValue = "10") int size){
//        return ResponseEntity.ok(resultService.getResultsByUser(studentId,page,size));
//
//    }
//
//    @DeleteMapping("/{resultId}")
//    public ResponseEntity<ApiResponse<String>> deleteResultById(@PathVariable Long resultId){
//        return ResponseEntity.ok(resultService.deleteResult(resultId));
//    }
//
//    @PutMapping("/{resultId}")
//    public ResponseEntity<ApiResponse<String>> grantedRetake(@PathVariable Long resultId){
//        return ResponseEntity.ok(resultService.grantRetake(resultId));
//    }
//
//    @GetMapping("/get-page")
//    public ResponseEntity<ApiResponse<ResPageable>> getAllResults(@RequestParam(defaultValue = "0")int page,
//                                                                  @RequestParam(defaultValue = "10")int size){
//        return ResponseEntity.ok(resultService.getAllResults(page,size));
//    }
//
//    @GetMapping("/byCategory/{categoryId}")
//    public ResponseEntity<ApiResponse<ResPageable>> getResultsByCategory(@PathVariable Long categoryId,
//                                                                         @RequestParam(defaultValue = "0")int page,
//                                                                         @RequestParam(defaultValue = "10")int size){
//        return ResponseEntity.ok(resultService.getResultsByCategory(categoryId,page,size));
//    }
//
//    @PostMapping("/start-test/{categoryId}")
//    public ResponseEntity<ApiResponse<List<ResponseQuestion>>> startTest(@AuthenticationPrincipal CustomUserDetails student,
//                                                                         @PathVariable Long categoryId) {
//        return ResponseEntity.ok(categoryService.startTest(student,categoryId));
//    }
//
//}
