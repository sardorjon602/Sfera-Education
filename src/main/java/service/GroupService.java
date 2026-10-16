package service;


import configuration.TrackAction;
import dto.ApiResponse;
import dto.GroupDTO;
import dto.request.ReqGroup;
import dto.request.ReqGroupDTO;
import dto.response.ResGroup;
import dto.response.ResPageable;
import entity.Category;
import entity.Group;
import entity.Room;
import entity.User;
import entity.enums.ActionType;
import entity.enums.GroupEnum;
import entity.enums.Role;
import entity.enums.WeekDays;
import exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import mapper.GroupMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import repository.CategoryRepository;
import repository.GroupRepository;
import repository.RoomRepository;
import repository.UserRepository;
import security.CustomUserDetails;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;
    private final GroupMapper groupMapper;
    private final CategoryRepository categoryRepository;


    @TrackAction(
            type = ActionType.GROUP_CREATED,
            description = "Group yaratildi"
    )
    public ApiResponse<String> saveGroup(ReqGroup reqGroup){
        User teacher = userRepository.findById(reqGroup.getTeacherId()).orElseThrow(
                () -> new DataNotFoundException("Teacher not found")
        );

        Room room = roomRepository.findById(reqGroup.getRoomId()).orElseThrow(
                () -> new DataNotFoundException("Room not found")
        );

        Category category = categoryRepository.findById(reqGroup.getCategoryId()).orElseThrow(
                () -> new DataNotFoundException("Category not found")
        );

        LocalTime startTime = LocalTime.parse(reqGroup.getStartTime());
        LocalTime endTime = LocalTime.parse(reqGroup.getEndTime());

        List<String> list = weekDays(reqGroup.getWeekDays().get(0)).stream().map(WeekDays::name).toList();

        if (groupRepository.existsByGroup(list,room.getId(),startTime, endTime)) {
            return ApiResponse.error("There is no room for the group at this time");
        }

        Group group = Group.builder()
                .name(reqGroup.getName())
                .startTime(startTime)
                .endTime(endTime)
                .teacher(teacher)
                .room(room)
                .weekDays(weekDays(reqGroup.getWeekDays().get(0)))
                .category(category)
                .build();
        groupRepository.save(group);
        return ApiResponse.success(null, "Group successfully saved");
    }




    public ApiResponse<String> updateGroup(ReqGroupDTO reqGroupDTO){
        Group group = groupRepository.findById(reqGroupDTO.getId()).orElseThrow(
                () -> new DataNotFoundException("Group not found")
        );

        LocalTime startTime = LocalTime.parse(reqGroupDTO.getStartTime());
        LocalTime endTime = LocalTime.parse(reqGroupDTO.getEndTime());


        if (groupRepository.existsByGroupForUpdate(weekDays(reqGroupDTO.getWeekDays().get(0)).stream().map(WeekDays::name).toList(),
                reqGroupDTO.getRoomId(), startTime, endTime, group.getId())) {
            return ApiResponse.error("There is no room for the group at this time");
        }

        User teacher = userRepository.findById(reqGroupDTO.getTeacherId()).orElseThrow(
                () -> new DataNotFoundException("Teacher not found")
        );

        Category category = categoryRepository.findById(reqGroupDTO.getCategoryId()).orElseThrow(
                () -> new DataNotFoundException("Category not found")
        );

        Room room = roomRepository.findById(reqGroupDTO.getRoomId()).orElseThrow(
                () -> new DataNotFoundException("Room not found")
        );


        group.setStartTime(startTime);
        group.setEndTime(endTime);
        group.setTeacher(teacher);
        group.setRoom(room);
        group.setName(reqGroupDTO.getName());
        group.setWeekDays(weekDays(reqGroupDTO.getWeekDays().get(0)));
        group.setCategory(category);

        groupRepository.save(group);
        return ApiResponse.success(null, "Group successfully updated");
    }



    public ApiResponse<String> deleteGroup(Long groupId){
        Group group = groupRepository.findById(groupId).orElseThrow(
                () -> new DataNotFoundException("Group not found")
        );

        group.setActive(false);
        groupRepository.save(group);
        return ApiResponse.success(null, "Group successfully deleted");
    }

    public ApiResponse<GroupDTO> getGroupById(Long id){
        Group group = groupRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException("Group not found")
        );
        return ApiResponse.success(groupMapper.groupDTO(group), "Success");
    }



    public ApiResponse<ResPageable> searchGroup(CustomUserDetails userDetails, String name, String teacherName,
                                                String roomName, int page, int size){
        Page<Group> groups;

        if (userDetails.getRole().equals(Role.ROLE_TEACHER.name())){
            User teacher = userDetails.getUser();
            groups = groupRepository.searchByGroup(name, teacher.getFullName(), roomName, PageRequest.of(page, size));
        } else {
            groups = groupRepository.searchByGroup(name, teacherName, roomName, PageRequest.of(page, size));
        }

        if(groups.getTotalElements() == 0){
            return ApiResponse.error("Group not found");
        }

        List<ResGroup> list = groups.stream().map(groupMapper::toDtoRes).toList();

        ResPageable resPageable = ResPageable.builder()
                .page(page)
                .size(size)
                .totalElements(groups.getTotalElements())
                .totalPage(groups.getTotalPages())
                .body(list)
                .build();
        return ApiResponse.success(resPageable, "Success");
    }


    public ApiResponse<List<ResGroup>> getAllGroup(CustomUserDetails userDetails){

        List<Group> groups;
        if (userDetails.getRole().equals(Role.ROLE_TEACHER.name())){
            groups = groupRepository.findAllByActiveTrueAndTeacher_Id(userDetails.getUser().getId());
        } else {
            groups = groupRepository.findAllByActiveTrue();
        }

        List<ResGroup> list = groups.stream().map(groupMapper::toDtoRes).toList();
        return ApiResponse.success(list, "Success");
    }


    public ApiResponse<List<LocalDate>> getLessonDaysForMonth(Long groupId, YearMonth yearMonth) {

        Group group = groupRepository.findById(groupId).orElseThrow(
                () -> new DataNotFoundException("Group not found")
        );

        // Guruhning dars kunlari
        List<WeekDays> lessonDays = group.getWeekDays();

        // Bir oylik boshlanish va tugash sanasi
        LocalDate start = yearMonth.atDay(1);
        LocalDate end = yearMonth.atEndOfMonth();

        List<LocalDate> lessonDates = new ArrayList<>();

        for (LocalDate date = start; !date.isAfter(end); date = date.plusDays(1)) {
            // Hafta kuni guruh jadvalidagi kunlar bilan mos bo'lsa
            if (lessonDays.contains(WeekDays.valueOf(date.getDayOfWeek().name()))) {
                lessonDates.add(date);
            }
        }

        return ApiResponse.success(lessonDates, "Success");
    }


    public List<WeekDays> weekDays(GroupEnum groupEnum){
        List<WeekDays> weekDays = new ArrayList<>();

        if (groupEnum.equals(GroupEnum.TOQ_KUNLAR)){
            weekDays.add(WeekDays.MONDAY);
            weekDays.add(WeekDays.WEDNESDAY);
            weekDays.add(WeekDays.FRIDAY);
        } else if (groupEnum.equals(GroupEnum.JUFT_KUNLAR)){
            weekDays.add(WeekDays.TUESDAY);
            weekDays.add(WeekDays.THURSDAY);
            weekDays.add(WeekDays.SATURDAY);
        } else {
            weekDays.add(WeekDays.MONDAY);
            weekDays.add(WeekDays.TUESDAY);
            weekDays.add(WeekDays.WEDNESDAY);
            weekDays.add(WeekDays.THURSDAY);
            weekDays.add(WeekDays.FRIDAY);
            weekDays.add(WeekDays.SATURDAY);
        }
        return weekDays;
    }

}
