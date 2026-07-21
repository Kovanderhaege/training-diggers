package fr.formation.taskmanager.factory;

import fr.formation.taskmanager.dto.TaskDTO;
import fr.formation.taskmanager.exception.business.InvalidTaskException;
import fr.formation.taskmanager.model.Priority;
import fr.formation.taskmanager.model.TaskId;
import fr.formation.taskmanager.model.task.AbstractTask;
import fr.formation.taskmanager.model.task.RecurringTask;
import fr.formation.taskmanager.model.task.SimpleTask;

import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;

public class TaskFactory {

    public static AbstractTask create(TaskDTO dto) {
        return switch (dto.type()) {
            case "SIMPLE" -> new SimpleTask(
                    new TaskId(dto.id()),
                    dto.title(),
                    dto.done(),
                    LocalDate.parse(dto.createdAt()),
                    Priority.valueOf(dto.priority())
            );
            case "RECURRING" -> new RecurringTask(
                    new TaskId(dto.id()),
                    dto.title(),
                    dto.done(),
                    LocalDate.parse(dto.createdAt()),
                    dto.intervalDays()
            );
            //on peut ajouter urgent task avec un simple case en plus, pour l'instant je trouve qu'elle a peu d'interet car on ne l'utilise qu'a 1 endroit mais c'est plus scalable comme ça
            default -> throw new InvalidTaskException(
                    "type", "type inconnu : " + dto.type()
            );
        };
    }

}
