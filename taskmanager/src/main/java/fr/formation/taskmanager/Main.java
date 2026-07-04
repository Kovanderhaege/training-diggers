package fr.formation.taskmanager;

import fr.formation.taskmanager.model.Priority;
import fr.formation.taskmanager.model.TaskId;
import fr.formation.taskmanager.model.task.AbstractTask;
import fr.formation.taskmanager.model.task.RecurringTask;
import fr.formation.taskmanager.model.task.SimpleTask;
import fr.formation.taskmanager.repository.InMemoryTaskRepository;
import fr.formation.taskmanager.repository.TaskRepository;
import fr.formation.taskmanager.service.DefaultTaskService;
import fr.formation.taskmanager.service.TaskService;
import fr.formation.taskmanager.utils.TaskAnalyzer;

import java.time.LocalDate;

public class Main {

    public static void main(String[] args) {
        TaskRepository taskRepository = new InMemoryTaskRepository();
        TaskService taskService = new DefaultTaskService(taskRepository);

        createTasks(taskService);

        System.out.println(TaskAnalyzer.buildReport(taskService.getActiveTasks()));

        System.out.println(taskService.partitionByStatus());

        System.out.println(taskService.findTask(new TaskId(999)).map(AbstractTask::getSummary).orElse("Could not find task"));

        try {
            taskService.addTask(new SimpleTask(
                    new TaskId(0),
                    "",
                    false,
                    LocalDate.now().minusDays(3),
                    Priority.HIGH
            ));
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void createTasks(TaskService taskService) {
        taskService.addTask(new SimpleTask(
                new TaskId(0),
                "Corriger le bug d'authentification",
                false,
                LocalDate.now().minusDays(3),
                Priority.HIGH
        ));

        taskService.addTask(new SimpleTask(
                new TaskId(1),
                "Mettre à jour la documentation",
                true,
                LocalDate.now().minusDays(10),
                Priority.LOW
        ));

        taskService.addTask(new SimpleTask(
                new TaskId(2),
                "Préparer la réunion client",
                false,
                LocalDate.now().minusDays(1),
                Priority.MEDIUM
        ));

        taskService.addTask(new RecurringTask(
                new TaskId(3),
                "Faire la sauvegarde hebdomadaire",
                true,
                LocalDate.now().minusDays(14),
                7
        ));

        taskService.addTask(new RecurringTask(
                new TaskId(4),
                "Nettoyer les logs",
                false,
                LocalDate.now().minusDays(5),
                3
        ));
    }

}
