package sfera.crmsfera.mapper;

import sfera.crmsfera.dto.response.ResTask;
import sfera.crmsfera.entity.Task;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {

    // Task → ResTask
    public ResTask toDto(Task task) {
        if (task == null) return null;

        return ResTask.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .deadline(task.getDeadline())
                .groupId(task.getGroup() != null ? task.getGroup().getId() : null)
                .groupName(task.getGroup() != null ? task.getGroup().getName() : null)
                .userId(task.getUser() != null ? task.getUser().getId() : null)
                .userName(task.getUser() != null ? task.getUser().getFullName():null)
                .build();
    }
}
