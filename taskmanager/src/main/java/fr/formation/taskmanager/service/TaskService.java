package fr.formation.taskmanager.service;

import fr.formation.taskmanager.dto.TaskDTO;
import fr.formation.taskmanager.model.task.AbstractTask;
import fr.formation.taskmanager.model.Priority;
import fr.formation.taskmanager.model.TaskId;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface TaskService {
    void addTask(AbstractTask task);

    void deleteTask(int id);

    void completeTask(int id);

    List<AbstractTask> getActiveTasks();

    List<AbstractTask> getTasksByPriority(Priority priority);

    List<AbstractTask> getAllTasks();

    AbstractTask findTask(int id);

    Map<Boolean, List<AbstractTask>> partitionByStatus();
}