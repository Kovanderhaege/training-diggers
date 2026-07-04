package fr.formation.taskmanager.model;

import java.time.LocalDate;
import java.util.Objects;

public abstract class AbstractTask {

    private TaskId id;

    private String title;

    private boolean done;

    private LocalDate createdAt;

    public AbstractTask(TaskId id, String title, boolean done, LocalDate createdAt) {
        this.id = id;
        this.title = title;
        this.done = done;
        this.createdAt = createdAt;
    }

    public void markAsDone(){
        this.done = true;
    }

    public abstract String getSummary();

    //Même avec un id (int) identique, comme l'objet TaskId sera différent le equals retournera false et hashcode sera différent
    public TaskId getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public boolean isDone() {
        return done;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    @Override
    public String toString() {
        return "AbstractTask{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", done=" + done +
                ", createdAt=" + createdAt +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        AbstractTask that = (AbstractTask) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, done, createdAt);
    }
}
