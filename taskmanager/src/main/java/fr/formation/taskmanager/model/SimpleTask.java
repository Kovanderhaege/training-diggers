package fr.formation.taskmanager.model;

import java.time.LocalDate;

public class SimpleTask extends AbstractTask{

    private Priority priority;

    public SimpleTask(TaskId id, String title, boolean done, LocalDate createdAt, Priority priority) {
        super(id, title, done, createdAt);
        this.priority = priority;
    }

    @Override
    public String getSummary() {
        return "[SIMPLE] " + getTitle() + " " + priority.toString();
    }
}
