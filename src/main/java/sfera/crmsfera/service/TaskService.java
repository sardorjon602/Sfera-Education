package sfera.crmsfera.service;

import sfera.crmsfera.dto.ApiResponse;
import sfera.crmsfera.dto.request.ReqGroupNotif;
import sfera.crmsfera.dto.request.ReqTask;
import sfera.crmsfera.dto.response.ResTask;
import sfera.crmsfera.entity.Group;
import sfera.crmsfera.entity.Task;
import sfera.crmsfera.entity.User;
import sfera.crmsfera.exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import sfera.crmsfera.mapper.TaskMapper;
import org.springframework.stereotype.Service;
import sfera.crmsfera.repository.GroupRepository;
import sfera.crmsfera.repository.TaskRepository;
import sfera.crmsfera.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final TaskMapper taskMapper;
    private final NotificationService notificationService;

    public ApiResponse<String> createTask(ReqTask reqTask){
        User user = null;
        Group group = null;
        if (reqTask.getUserId() != null){
            user = userRepository.findById(reqTask.getUserId()).orElseThrow(
                    () -> new DataNotFoundException("User topilmadi")
            );
        } else if (reqTask.getGroupId() != null) {
            group = groupRepository.findById(reqTask.getGroupId()).orElseThrow(
                    () -> new DataNotFoundException("Group topilmadi")
            );
        }

        Task task = Task.builder()
                .title(reqTask.getTitle())
                .description(reqTask.getDescription())
                .deadline(reqTask.getDeadline())
                .group(group)
                .user(user)
                .build();
        taskRepository.save(task);

        if (group != null){
            notificationService.sendGroupNotification(ReqGroupNotif.builder()
                    .groupId(group.getId())
                    .title("Sfera Academy xabarnomasi")
                    .description("Sizga " + task.getTitle() + " nomli vazifa yuklandi! " +
                            "Siz " + task.getDeadline() + " sanagacha taskni tugatishingiz kerak")
                    .build());
        }
        return ApiResponse.success(null, "Task created");
    }



    public ApiResponse<String> updateTask(ReqTask reqTask){
        Task task = taskRepository.findById(reqTask.getTaskId()).orElseThrow(
                () -> new DataNotFoundException("Task topilmadi")
        );

        User user = null;
        Group group = null;
        if (reqTask.getUserId() != null){
            user = userRepository.findById(reqTask.getUserId()).orElseThrow(
                    () -> new DataNotFoundException("User topilmadi")
            );
        } else if (reqTask.getGroupId() != null) {
            group = groupRepository.findById(reqTask.getGroupId()).orElseThrow(
                    () -> new DataNotFoundException("Group topilmadi")
            );
        }

        task.setTitle(reqTask.getTitle());
        task.setDescription(reqTask.getDescription());
        task.setDeadline(reqTask.getDeadline());
        task.setGroup(group);
        task.setUser(user);
        taskRepository.save(task);
        return ApiResponse.success(null, "Task updated");
    }


    public ApiResponse<String> deleteTask(Long taskId){
        Task task = taskRepository.findById(taskId).orElseThrow(
                () -> new DataNotFoundException("Task topilmadi")
        );
        taskRepository.delete(task);
        return ApiResponse.success(null, "Task deleted");
    }


    public ApiResponse<ResTask> getTask(Long taskId){
        Task task = taskRepository.findById(taskId).orElseThrow(
                () -> new DataNotFoundException("Task topilmadi")
        );

        return ApiResponse.success(taskMapper.toDto(task), "Task found");
    }


    public ApiResponse<List<ResTask>> getAllTasks(){
        List<ResTask> list = taskRepository.findAll().stream().map(taskMapper::toDto).toList();
        return ApiResponse.success(list, "Tasks found");
    }

}
