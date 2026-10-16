package controller;


import configuration.TrackAction;
import dto.ApiResponse;
import dto.request.ReqCategory;
import dto.request.ReqCategoryDTO;
import dto.response.ResCategory;
import dto.response.ResPageable;
import entity.enums.ActionType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @TrackAction(
            type = ActionType.CATEGORY_CREATED,
            description = "Category yaratildi"
    )
    @PostMapping
    public ResponseEntity<ApiResponse<String>> saveCategory(@RequestBody ReqCategory reqCategory){
        return ResponseEntity.ok(categoryService.saveCategory(reqCategory));
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse<String>> updateCategory(@RequestBody ReqCategoryDTO reqCategoryDTO){
        return ResponseEntity.ok(categoryService.updateCategory(reqCategoryDTO));
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponse<String>> deleteCategory(@PathVariable Long categoryId){
        return ResponseEntity.ok(categoryService.deleteCategory(categoryId));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ResCategory>>> getCategories(){
        return ResponseEntity.ok(categoryService.getAllCategory());
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<ResPageable>> searchCategory(@RequestParam(required = false) String name,
                                                                   @RequestParam(defaultValue = "0") int page,
                                                                   @RequestParam(defaultValue = "10") int size){
        return ResponseEntity.ok(categoryService.searchCategory(name, page, size));
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<ApiResponse<ResCategory>> getCategory(@PathVariable Long categoryId){
        return ResponseEntity.ok(categoryService.getCategoryById(categoryId));
    }

//    @PostMapping("/start-test/{categoryId}")
//    public ResponseEntity<ApiResponse<List<ResponseQuestion>>> startTest(@AuthenticationPrincipal CustomUserDetails student,
//                                                                         @PathVariable Long categoryId) {
//        return ResponseEntity.ok(categoryService.startTest(student,categoryId));
//    }

}
