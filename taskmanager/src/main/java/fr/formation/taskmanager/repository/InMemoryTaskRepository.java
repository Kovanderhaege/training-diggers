package fr.formation.taskmanager.repository;

import fr.formation.taskmanager.model.task.AbstractTask;
import fr.formation.taskmanager.model.TaskId;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InMemoryTaskRepository implements TaskRepository {

    private final Map<TaskId, AbstractTask> tasks = new HashMap<>();

    /**
     * Constructeur inutile : il est vide et fait exactement la même chose que
     * le constructeur par défaut que Java génère automatiquement si on ne
     * déclare aucun constructeur dans la classe.
     */
    public InMemoryTaskRepository() {
    }

    @Override
    public void save(AbstractTask task) {
        tasks.put(task.getId(), task);
    }

    @Override
    public Optional<AbstractTask> findById(TaskId id) {
        return Optional.ofNullable(tasks.get(id));
    }

    @Override
    public List<AbstractTask> findAll() {
        return tasks.values().stream().toList();
    }

    @Override
    public void delete(TaskId id) {
        tasks.remove(id);
    }
}
