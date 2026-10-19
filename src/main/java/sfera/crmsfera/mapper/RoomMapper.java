package sfera.crmsfera.mapper;

import sfera.crmsfera.dto.DayStat;
import sfera.crmsfera.dto.RoomDTO;
import sfera.crmsfera.dto.response.ResGroupDTO;
import sfera.crmsfera.dto.response.ResRoom;
import sfera.crmsfera.entity.Room;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RoomMapper {

    public RoomDTO roomDTO(Room room) {
        return RoomDTO.builder()
                .id(room.getId())
                .name(room.getName())
                .build();
    }


    public ResRoom resRoom(Room room, List<ResGroupDTO> schedules, List<DayStat> weeklyStats) {
        return ResRoom.builder()
                .id(room.getId())
                .name(room.getName())
                .schedules(schedules)
                .weeklyStats(weeklyStats)
                .build();
    }
}
