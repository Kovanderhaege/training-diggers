package fr.formation.taskmanager.model.task;

import fr.formation.taskmanager.model.TaskId;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class RecurringTask extends AbstractTask {

    private int intervalDays;

    public RecurringTask(TaskId id, String title, boolean done, LocalDate createdAt, int intervalDays) {
        super(id, title, done, createdAt);
        this.intervalDays = intervalDays;
    }

    @Override
    public String getSummary(){
        return "[SIMPLE] " + getTitle() + " (tous les " + intervalDays + " jours)";
    }

    public LocalDate getNextOccurrence() {
        if (getCreatedAt() == null) {
            throw new IllegalArgumentException("dateCreation ne peut pas être null");
        }
        if (intervalDays <= 0) {
            throw new IllegalArgumentException("intervalDays doit être > 0");
        }

        LocalDate now = LocalDate.now();

        if (!getCreatedAt().isBefore(now)) {
            return getCreatedAt();
        }

        long passedDays = ChronoUnit.DAYS.between(getCreatedAt(), now);
        long passedOccurrences = passedDays / intervalDays;

        LocalDate next = getCreatedAt().plusDays(passedOccurrences * intervalDays);

        if (!next.isAfter(now)) {
            next = next.plusDays(intervalDays);
        }

        return next;
    }
}
