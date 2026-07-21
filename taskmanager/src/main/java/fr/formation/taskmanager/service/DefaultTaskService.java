package fr.formation.taskmanager.service;

import fr.formation.taskmanager.exception.business.InvalidTaskException;
import fr.formation.taskmanager.exception.business.TaskNotFoundException;
import fr.formation.taskmanager.model.Priority;
import fr.formation.taskmanager.model.task.AbstractTask;
import fr.formation.taskmanager.model.task.SimpleTask;
import fr.formation.taskmanager.repository.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class DefaultTaskService implements TaskService{
    private static final Logger log = LoggerFactory.getLogger(DefaultTaskService.class);

    private final TaskRepository taskRepository;

    public DefaultTaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public void addTask(AbstractTask task) {
        if(task.getTitle().isEmpty()){
            throw new InvalidTaskException("title", "is empty");
        }
        taskRepository.save(task);
    }

    @Override
    public void deleteTask(int id) {
        taskRepository.delete(id);
    }

    @Override
    public void completeTask(int id) {
        MDC.put("opId", UUID.randomUUID().toString());
        MDC.put("taskId", String.valueOf(id));
        try {
            log.debug("Recherche tâche id={}", id);
            var task = taskRepository.findById(id)
                    .orElseThrow(() -> new TaskNotFoundException(id));
            task.markAsDone();
            taskRepository.save(task);
            log.info("Tâche terminée : id={}, titre='{}'", id, task.getTitle()); // INFO
        } catch (TaskNotFoundException e) {
            log.warn("Complétion impossible : id={}", id); // WARN
            throw e;
        } finally {
            MDC.clear(); // crash mémoire si on ne clear pas
        }
    }

    @Override
    public List<AbstractTask> getActiveTasks() {
        return taskRepository.findAll()
                .stream()
                .filter(task -> !task.isDone())
                .toList();
    }

    @Override
    public List<AbstractTask> getAllTasks() {
        return taskRepository.findAll();
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
    public AbstractTask findTask(int id) {
        if(taskRepository.findById(id).isPresent()){
            return taskRepository.findById(id).get();
        }
        throw new TaskNotFoundException(id);
    }

    @Override
    public Map<Boolean, List<AbstractTask>> partitionByStatus() {
        return getAllTasks().stream().collect(Collectors.partitioningBy(AbstractTask::isDone));
    }
}
