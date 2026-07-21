package fr.formation.taskmanager.exception.business;

public class TaskNotFoundException extends BusinessException {
    private final int taskId;

    public TaskNotFoundException(int id) {
        super("Tâche introuvable avec l'id : " + id);
        this.taskId = id;
    }

    public String code() {
        return "TASK_NOT_FOUND";
    }

    public int getTaskId() {
        return taskId;
    }
}