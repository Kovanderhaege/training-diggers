package fr.formation.taskmanager.utils;

import fr.formation.taskmanager.model.Priority;
import fr.formation.taskmanager.model.task.AbstractTask;
import fr.formation.taskmanager.model.task.SimpleTask;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class TaskAnalyzer {

    public static long countDone(List<AbstractTask> tasks) {
        return  tasks.stream()
                .filter(AbstractTask::isDone)
                .count();
    }

    public static List<String> getTitlesUpperCase(List<AbstractTask> tasks) {
        return tasks.stream()
                .map(AbstractTask::getTitle)
                .map(String::toUpperCase)
                .toList();
    }

    //un get sur un optional vide throw une erreur, on peut faire isPresent dessus pour s'assurer qu'il n'est pas vide
    public static Optional<AbstractTask> findMostUrgent(List<AbstractTask> tasks) {
        return tasks.stream()
                .filter(abstractTask -> abstractTask instanceof SimpleTask)
                .filter(abstractTask -> !abstractTask.isDone())
                .max(Comparator.comparing(AbstractTask::getCreatedAt));

//                pas trop compris, dans l'exo on demande un optional task en valeur de retour mais aussi d'afficher le résumé ou "aucune tache urgente"

//                .ifPresentOrElse(abstractTask -> System.out.printf(abstractTask.getSummary()),
//                        () -> System.out.println("Aucune tâche urgente"));

//                 si ma valeur de retour doit etre un string finalement :
//               .ifPresentOrElse(abstractTask -> return abstractTask.getSummary(),
//                        () -> return "Aucune tâche urgente");
    }

    public static Map<Priority, Long> countByPriority(List<AbstractTask>  tasks) {
        return tasks.stream()
                .filter(abstractTask -> abstractTask instanceof SimpleTask)
                .collect(Collectors.groupingBy(abstractTask -> ((SimpleTask) abstractTask).getPriority(), Collectors.counting()));
    }


    public static String buildReport(List<AbstractTask> tasks) {
        return tasks.stream()
                .map(AbstractTask::getSummary)
                .collect(Collectors.joining("\n"));
    }
}
