package sfera.crmsfera.controller;

import sfera.crmsfera.dto.ApiResponse;
import sfera.crmsfera.dto.StudentDTO;
import sfera.crmsfera.dto.request.ReqStudent;
import sfera.crmsfera.dto.response.ResPageable;
import sfera.crmsfera.dto.response.ResStudent;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import sfera.crmsfera.security.CustomUserDetails;
import sfera.crmsfera.service.AuthService;
import sfera.crmsfera.service.MarkService;
import sfera.crmsfera.service.StudentService;

import java.util.List;

@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;
    private final MarkService markService;
    private final AuthService authService;

    @GetMapping
    public ResponseEntity<ApiResponse<ResPageable>> getStudentsByPage(@RequestParam(defaultValue = "0") int page,
                                                                      @RequestParam(defaultValue = "10") int size,
                                                                      @RequestParam(required = false) String name,
                                                                      @RequestParam(required = false) String phone) {
        return ResponseEntity.ok(studentService.getStudents(name,phone,page,size));
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<ApiResponse<ResStudent>> getById(@PathVariable Long studentId) {
        return ResponseEntity.ok(studentService.getById(studentId));
    }
    @DeleteMapping("/{studentId}")
    public ResponseEntity<ApiResponse<String>> deleteById(@PathVariable Long studentId) {
        return ResponseEntity.ok(studentService.delete(studentId));
    }

    @PutMapping
    public ResponseEntity<ApiResponse<String>> update(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                                      @RequestBody StudentDTO studentDTO){
        return ResponseEntity.ok(studentService.update(customUserDetails, studentDTO));

    }

    @PutMapping("/update-group")
    public ResponseEntity<ApiResponse<String>> updateGroup(@RequestParam Long studentId,
                                                           @RequestParam Long groupId){
        return ResponseEntity.ok(studentService.updateGroup(studentId, groupId));
    }


    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPER_ADMIN')")
    @PostMapping("/saveStudent")
    public ResponseEntity<ApiResponse<String>> studentLogin(@RequestBody ReqStudent reqStudent){
        return ResponseEntity.ok(authService.saveStudent(reqStudent));
    }



    @GetMapping("/list")
    public ResponseEntity<ApiResponse<List<ResStudent>>> getList(){
        return ResponseEntity.ok(studentService.getStudentList());
    }


    @GetMapping("/inactive-student")
    public ResponseEntity<ApiResponse<List<ResStudent>>> getInactiveList(){
        return ResponseEntity.ok(studentService.getDeActiveStudentList());
    }

}
