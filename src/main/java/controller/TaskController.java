package controller;

import dto.ApiResponse;
import dto.request.ReqTask;
import dto.response.ResTask;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.TaskService;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;


   @PostMapping
    public ResponseEntity<ApiResponse<String>> saveTask(@RequestBody ReqTask reqTask){
       return ResponseEntity.ok(taskService.createTask(reqTask));
   }


   @PutMapping("/update")
    public ResponseEntity<ApiResponse<String>> updateTask(@RequestBody ReqTask reqTask){
       return ResponseEntity.ok(taskService.updateTask(reqTask));
   }


   @DeleteMapping("/{taskId}")
    public ResponseEntity<ApiResponse<String>> deleteTask(@PathVariable Long taskId){
       return ResponseEntity.ok(taskService.deleteTask(taskId));
   }


   @GetMapping
    public ResponseEntity<ApiResponse<List<ResTask>>> getAllTasks(){
       return ResponseEntity.ok(taskService.getAllTasks());
   }


   @GetMapping("/{taskId}")
    public ResponseEntity<ApiResponse<ResTask>> getTask(@PathVariable Long taskId){
       return ResponseEntity.ok(taskService.getTask(taskId));
   }


}
