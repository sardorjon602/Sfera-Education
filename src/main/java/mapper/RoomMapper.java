package mapper;

import dto.DayStat;
import dto.RoomDTO;
import dto.response.ResGroupDTO;
import dto.response.ResRoom;
import entity.Room;
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
