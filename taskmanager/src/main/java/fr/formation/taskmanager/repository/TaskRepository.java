package fr.formation.taskmanager.repository;

import fr.formation.taskmanager.model.task.AbstractTask;
import fr.formation.taskmanager.model.TaskId;

import java.util.List;
import java.util.Optional;

public interface TaskRepository {

    void save(AbstractTask task);

    Optional<AbstractTask> findById(int id);

    List<AbstractTask> findAll();

    void delete(int id);
}