package sfera.crmsfera.service;


import sfera.crmsfera.configuration.TrackAction;
import sfera.crmsfera.dto.ApiResponse;
import sfera.crmsfera.dto.DayStat;
import sfera.crmsfera.dto.RoomDTO;
import sfera.crmsfera.dto.request.ReqRoom;
import sfera.crmsfera.dto.response.ResGroupDTO;
import sfera.crmsfera.dto.response.ResPageable;
import sfera.crmsfera.dto.response.ResRoom;
import sfera.crmsfera.entity.Group;
import sfera.crmsfera.entity.Room;
import sfera.crmsfera.entity.enums.ActionType;
import sfera.crmsfera.exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import sfera.crmsfera.mapper.GroupMapper;
import sfera.crmsfera.mapper.RoomMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import sfera.crmsfera.repository.GroupRepository;
import sfera.crmsfera.repository.RoomRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    private final RoomMapper roomMapper;
    private final GroupRepository groupRepository;
    private final GroupMapper groupMapper;

    @TrackAction(
            type = ActionType.ROOM_CREATED,
            description = "Xona yaratildi"
    )
    public ApiResponse<String> saveRoom(RoomDTO roomDTO){
        if (roomRepository.existsByName(roomDTO.getName())) {
            return ApiResponse.error("Name already exists");
        }

        Room room = Room.builder()
                .name(roomDTO.getName())

                .build();
        roomRepository.save(room);
        return ApiResponse.success(null, "Success");
    }


    @TrackAction(
            type = ActionType.ROOM_UPDATED,
            description = "Xona yaratildi"
    )
    public ApiResponse<String> updateRoom(ReqRoom reqRoom){
        Room room = roomRepository.findById(reqRoom.getId()).orElseThrow(
                () -> new DataNotFoundException("Room not found")
        );

        room.setName(reqRoom.getName());
        roomRepository.save(room);
        return ApiResponse.success(null, "Success");
    }


    public ApiResponse<String> deleteRoom(Long id){
        Room room = roomRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException("Room not found")
        );

        int size = groupRepository.findAllByRoomIdAndActiveTrue(room.getId()).size();
        if (size == 0){
            room.setActive(false);
        } else {
            return ApiResponse.error("Cannot delete room");
        }

        roomRepository.save(room);
        return ApiResponse.success(null, "Success");
    }



    public ApiResponse<ResRoom> getRoom(Long id){
        Room room = roomRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException("Room not found")
        );
        List<Group> groups = groupRepository.findAllByRoomIdAndActiveTrue(room.getId());

        List<ResGroupDTO> schedules = groups.stream().map(groupMapper::toRes).toList();

        List<DayStat> weeklyStats = RoomScheduleStats.buildWeeklyStats(groups);


        return ApiResponse.success(roomMapper.resRoom(room, schedules,weeklyStats), "Success");
    }


    public ApiResponse<List<RoomDTO>> getAllRooms(){
        List<RoomDTO> rooms = roomRepository.findAll().stream().map(roomMapper::roomDTO).toList();
        return ApiResponse.success(rooms, "Success");
    }


    public ApiResponse<ResPageable> searchRooms(String name, int page, int size){
        Page<Room> rooms = roomRepository.searchRooms(name, PageRequest.of(page, size));
        if (rooms.getTotalElements() == 0){
            return ApiResponse.error("Room not found");
        }

        List<RoomDTO> list = rooms.getContent().stream().map(roomMapper::roomDTO).toList();
        ResPageable resPageable = ResPageable.builder()
                .page(page)
                .size(size)
                .totalElements(rooms.getTotalElements())
                .totalPage(rooms.getTotalPages())
                .body(list)
                .build();
        return ApiResponse.success(resPageable, "Success");
    }
}
