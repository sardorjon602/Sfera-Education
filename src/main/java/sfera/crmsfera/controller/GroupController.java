package sfera.crmsfera.controller;

import sfera.crmsfera.dto.ApiResponse;
import sfera.crmsfera.dto.GroupDTO;
import sfera.crmsfera.dto.request.ReqGroup;
import sfera.crmsfera.dto.request.ReqGroupDTO;
import sfera.crmsfera.dto.response.ResGroup;
import sfera.crmsfera.dto.response.ResPageable;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import sfera.crmsfera.security.CustomUserDetails;
import sfera.crmsfera.service.GroupService;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@RestController
@RequestMapping("/group")
@RequiredArgsConstructor
public class GroupController {
    private final GroupService groupService;


    @PostMapping
    @Operation(description = "JUFT_KUNLAR, TOQ_KUNLAR, BOSHQA_KUNLAR")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_TEACHER', 'ROLE_SUPER_ADMIN')")
    public ResponseEntity<ApiResponse<String>> saveGroup(@RequestBody ReqGroup reqGroup){
        return ResponseEntity.ok(groupService.saveGroup(reqGroup));
    }


    @PutMapping("/update")
    @Operation(description = "JUFT_KUNLAR, TOQ_KUNLAR, BOSHQA_KUNLAR")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER', 'ROLE_SUPER_ADMIN')")
    public ResponseEntity<ApiResponse<String>> updateGroup(@RequestBody ReqGroupDTO reqGroupDTO){
        return ResponseEntity.ok(groupService.updateGroup(reqGroupDTO));
    }


    @DeleteMapping("/{groupId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER', 'ROLE_SUPER_ADMIN')")
    public ResponseEntity<ApiResponse<String>> deleteGroup(@PathVariable Long groupId){
        return ResponseEntity.ok(groupService.deleteGroup(groupId));
    }


    @GetMapping("/{groupId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER', 'ROLE_STUDENT', 'ROLE_PARENT','ROLE_SUPER_ADMIN')")
    @Operation(summary = "Guruhni bittasini kurish")
    public ResponseEntity<ApiResponse<GroupDTO>> getGroup(@PathVariable Long groupId){
        return ResponseEntity.ok(groupService.getGroupById(groupId));
    }


    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_TEACHER','ROLE_SUPER_ADMIN')")
    @Operation(summary = "Guruhni filter qilish")
    public ResponseEntity<ApiResponse<ResPageable>> getAllGroup(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                                @RequestParam(required = false) String name,
                                                                @RequestParam(required = false) String teacherName,
                                                                @RequestParam(required = false) String roomName,
                                                                @RequestParam(defaultValue = "0") int page,
                                                                @RequestParam(defaultValue = "10") int size){
        return ResponseEntity.ok(groupService.searchGroup(userDetails,name, teacherName, roomName, page, size));
    }


    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_TEACHER', 'ROLE_SUPER_ADMIN')")
    @Operation(summary = "Guruhni hammasini kurish")
    public ResponseEntity<ApiResponse<List<ResGroup>>> getAllGroup(
            @AuthenticationPrincipal CustomUserDetails customUserDetails){
        return ResponseEntity.ok(groupService.getAllGroup(customUserDetails));
    }


    @GetMapping("/getDays")
    @Operation(description = "yearMonth = format -> yyyy-mm")
    public ResponseEntity<ApiResponse<List<LocalDate>>> getDays(@RequestParam Long groupId,
                                                                @RequestParam YearMonth yearMonth){
        return ResponseEntity.ok(groupService.getLessonDaysForMonth(groupId, yearMonth));
    }
}
