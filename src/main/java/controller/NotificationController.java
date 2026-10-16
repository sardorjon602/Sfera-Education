package controller;

import dto.ApiResponse;
import dto.request.IdList;
import dto.request.ReqGroupNotif;
import dto.request.ReqNotification;
import dto.response.ResNotification;
import dto.response.ResPageable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import security.CustomUserDetails;
import service.NotificationService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notification")
public class NotificationController {
    private final NotificationService notificationService;

    @PostMapping
    public ResponseEntity<ApiResponse<String>> createNotification(@RequestBody ReqNotification req) {
        return ResponseEntity.ok(notificationService.saveNotification(req));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<ResPageable>> getNotifications(@RequestParam(defaultValue = "0") int page ,
                                                                     @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(notificationService.getNotifications(page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ResNotification>> getNotificationById(@PathVariable Long id) {
        return ResponseEntity.ok(notificationService.getNotificationById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable Long id) {
        return ResponseEntity.ok(notificationService.deleteNotificationById(id));
    }


    @GetMapping("/my")
    public ResponseEntity<ApiResponse<List<ResNotification>>> getMyNotifications(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        return ResponseEntity.ok(notificationService.getMyNotifications(customUserDetails));
    }


    @PutMapping("/read")
    public ResponseEntity<ApiResponse<String>> readNotification(@RequestBody IdList idList) {
        return ResponseEntity.ok(notificationService.readNotification(idList));
    }


    @GetMapping("/count")
    public ResponseEntity<ApiResponse<Long>> countNotification(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        return ResponseEntity.ok(notificationService.countMyNotifications(customUserDetails));
    }

    @PostMapping("/withGroup")
    public ResponseEntity<ApiResponse<String>> createNotificationWithGroup(@RequestBody ReqGroupNotif reqGroupNotif) {
        return ResponseEntity.ok(notificationService.sendGroupNotification(reqGroupNotif));
    }

}
