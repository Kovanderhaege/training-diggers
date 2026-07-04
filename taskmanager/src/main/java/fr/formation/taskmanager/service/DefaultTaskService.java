package fr.formation.taskmanager.service;

import fr.formation.taskmanager.model.AbstractTask;
import fr.formation.taskmanager.model.Priority;
import fr.formation.taskmanager.model.SimpleTask;
import fr.formation.taskmanager.model.TaskId;
import fr.formation.taskmanager.repository.TaskRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class DefaultTaskService implements TaskService{

    private TaskRepository taskRepository;

    public DefaultTaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public void addTask(AbstractTask task) {
        taskRepository.save(task);
    }

    @Override
    public void completeTask(TaskId id) {
        taskRepository.findById(id)
                .ifPresentOrElse(AbstractTask::markAsDone,
                () -> {throw new IllegalStateException("Task with id " + id + " not found");}
        );
    }

    @Override
    public List<AbstractTask> getActiveTasks() {
        return taskRepository.findAll()
                .stream()
                .filter(task -> !task.isDone())
                .toList();
    }

    //si on ajoute urgent task elle sera filtrée aussi
    @Override
    public List<AbstractTask> getTasksByPriority(Priority priority) {
        return taskRepository.findAll()
                .stream()
                .filter(task -> task instanceof SimpleTask)
                .filter(task -> priority.equals(((SimpleTask) task).getPriority()))
                .toList();
    }

    @Override
    public Optional<AbstractTask> findTask(TaskId id) {
        return taskRepository.findById(id);
    }

    @Override
    public Map<Boolean, List<AbstractTask>> partitionByStatus() {
        return taskRepository.findAll().stream().collect(Collectors.partitioningBy(task -> task.isDone()));
    }
}
