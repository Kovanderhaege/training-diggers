package fr.formation.taskmanager.repository;

import fr.formation.taskmanager.model.task.AbstractTask;
import fr.formation.taskmanager.model.TaskId;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InMemoryTaskRepository implements TaskRepository {

    private final Map<Integer, AbstractTask> tasks = new HashMap<>();

    public InMemoryTaskRepository() {
    }

    @Override
    public void save(AbstractTask task) {
        tasks.put(task.getId().id(), task);
    }

    @Override
    public Optional<AbstractTask> findById(int id) {
        return Optional.ofNullable(tasks.get(id));
    }

    @Override
    public List<AbstractTask> findAll() {
        return tasks.values().stream().toList();
    }

    @Override
    public void delete(int id) {
        tasks.remove(id);
    }
}
