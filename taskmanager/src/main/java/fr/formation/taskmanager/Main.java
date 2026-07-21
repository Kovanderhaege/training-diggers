package fr.formation.taskmanager;

import fr.formation.taskmanager.controller.TaskController;
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
     AppConfig appConfig = new AppConfig();
     TaskController taskController = appConfig.buildController();

    }


}
