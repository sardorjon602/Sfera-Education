package controller;

import dto.ApiResponse;
import dto.request.ReqNews;
import dto.request.ReqNewsDTO;
import dto.response.ResNews;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.NewsService;

import java.util.List;

@RestController
@RequestMapping("/news")
@RequiredArgsConstructor
public class NewsController {

    private final NewsService newsService;

    @PostMapping
    public ResponseEntity<ApiResponse<String>> add(@RequestBody ReqNews req) {
        return ResponseEntity.ok(newsService.add(req));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ResNews>>> getAll() {
        return ResponseEntity.ok(newsService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ResNews>> getOne(@PathVariable Long id) {
        return ResponseEntity.ok(newsService.getOne(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable Long id) {
        return ResponseEntity.ok(newsService.delete(id));
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse<String >> updateNews(@RequestBody ReqNewsDTO reqNewsDTO){
        return ResponseEntity.ok(newsService.updateNews(reqNewsDTO));
    }

}
