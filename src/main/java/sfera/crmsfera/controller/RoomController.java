package sfera.crmsfera.controller;

import sfera.crmsfera.dto.ApiResponse;
import sfera.crmsfera.dto.RoomDTO;
import sfera.crmsfera.dto.request.ReqRoom;
import sfera.crmsfera.dto.response.ResPageable;
import sfera.crmsfera.dto.response.ResRoom;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sfera.crmsfera.service.RoomService;

import java.util.List;

@RestController
@RequestMapping("/room")
@RequiredArgsConstructor
public class RoomController {
    private final RoomService roomService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPER_ADMIN')")
    public ResponseEntity<ApiResponse<String>> saveRoom(@RequestBody RoomDTO roomDTO){
        return ResponseEntity.ok(roomService.saveRoom(roomDTO));
    }


    @PutMapping("/update")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPER_ADMIN')")
    public ResponseEntity<ApiResponse<String>> updateRoom(@RequestBody ReqRoom reqRoom){
        return ResponseEntity.ok(roomService.updateRoom(reqRoom));
    }


    @DeleteMapping("/{roomId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPER_ADMIN')")
    public ResponseEntity<ApiResponse<String>> deleteRoom(@PathVariable Long roomId){
        return ResponseEntity.ok(roomService.deleteRoom(roomId));
    }



    @GetMapping
    public ResponseEntity<ApiResponse<List<RoomDTO>>> getAllRooms(){
        return ResponseEntity.ok(roomService.getAllRooms());
    }


    @GetMapping("/search")
    public ResponseEntity<ApiResponse<ResPageable>> searchRooms(@RequestParam(required = false) String name,
                                                                @RequestParam(defaultValue = "0") int page,
                                                                @RequestParam(defaultValue = "10") int size){
        return ResponseEntity.ok(roomService.searchRooms(name, page, size));
    }


    @GetMapping("/{roomId}")
    public ResponseEntity<ApiResponse<ResRoom>> getRoomById(@PathVariable Long roomId){
        return ResponseEntity.ok(roomService.getRoom(roomId));
    }
}
