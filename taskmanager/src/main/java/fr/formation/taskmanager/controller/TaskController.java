package fr.formation.taskmanager.controller;

import fr.formation.taskmanager.dto.TaskDTO;
import fr.formation.taskmanager.dto.TaskMapper;
import fr.formation.taskmanager.model.TaskId;
import fr.formation.taskmanager.service.TaskService;

import java.util.List;

public class TaskController {
    // Injection par constructeur — JAMAIS "new" dans les champs
    private final TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }


    public List<TaskDTO> getAllTasks() {
        return TaskMapper.mapTasksToTaskDTOs(service.getAllTasks());
    }

    public TaskDTO getTaskById(int id) {
        return TaskMapper.mapTaskToTaskDTO(service.findTask(id));
    }
    public void createTask(TaskDTO dto) {
        service.addTask(TaskMapper.mapTaskDTOToTask(dto));
    }
    public void completeTask(int id) {
        service.completeTask(id);
    }
    public void deleteTask(int id) {
        service.deleteTask(id);
    }
}