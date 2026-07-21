package fr.formation.taskmanager.dto;

import fr.formation.taskmanager.exception.business.InvalidTaskException;
import fr.formation.taskmanager.factory.TaskFactory;
import fr.formation.taskmanager.model.Priority;
import fr.formation.taskmanager.model.TaskId;
import fr.formation.taskmanager.model.task.AbstractTask;
import fr.formation.taskmanager.model.task.RecurringTask;
import fr.formation.taskmanager.model.task.SimpleTask;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class TaskMapper {

    public static TaskDTO mapTaskToTaskDTO(AbstractTask task) {
        return switch (task) {
            case SimpleTask simpleTask ->
                    new TaskDTO(simpleTask.getId().id(), simpleTask.getTitle(), "SIMPLE", simpleTask.getPriority().name(), null, simpleTask.isDone(), simpleTask.getCreatedAt().toString(), simpleTask.getSummary());
            case RecurringTask recurringTask ->
                    new TaskDTO(recurringTask.getId().id(), recurringTask.getTitle(), "RECURRING", null, recurringTask.getIntervalDays(), recurringTask.isDone(), recurringTask.getCreatedAt().toString(), recurringTask.getSummary());
            case null, default -> throw new IllegalArgumentException("task must not be null");
        };
    }

    public static List<TaskDTO> mapTasksToTaskDTOs(List<AbstractTask> tasks) {
        return tasks.stream().map(TaskMapper::mapTaskToTaskDTO).collect(Collectors.toList());
    }

    public static AbstractTask mapTaskDTOToTask(TaskDTO taskDTO) {
        return TaskFactory.create(taskDTO);
    }
}
