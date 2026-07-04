package fr.formation.taskmanager.model.task;

import fr.formation.taskmanager.model.Priority;
import fr.formation.taskmanager.model.TaskId;

import java.time.LocalDate;

public class SimpleTask extends AbstractTask {

    private Priority priority;

    public SimpleTask(TaskId id, String title, boolean done, LocalDate createdAt, Priority priority) {
        super(id, title, done, createdAt);
        this.priority = priority;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    @Override
    public String getSummary() {
        return "[SIMPLE] " + getTitle() + " " + priority.toString();
    }
}
