package fr.formation.taskmanager;

import fr.formation.taskmanager.controller.TaskController;
import fr.formation.taskmanager.dto.TaskMapper;
import fr.formation.taskmanager.factory.TaskFactory;
import fr.formation.taskmanager.repository.InMemoryTaskRepository;
import fr.formation.taskmanager.repository.TaskRepository;
import fr.formation.taskmanager.service.DefaultTaskService;
import fr.formation.taskmanager.service.TaskService;

public class AppConfig {
    //Spring gère lui meme le cycle de vie des beans annotés service et components, il scan le projet pour résoudre automatiquement les dépendance au démarrage de l'app
    public TaskController buildController() {
        TaskRepository repo = new InMemoryTaskRepository();

        //il y a un petit problème de cohérence dans le tp, on ne fait déjà aucun new Simple/Recurring task dans le service, puisque c'est pris en compte dans le mapper, qui doit etre stateless donc pas de reference a la factory, donc j'ai modifié un peu la factory pour que la méthode create soit static, par contre les dto et task créés partagent le meme id
        TaskService service = new DefaultTaskService(repo);

        return new TaskController(service);
    }
}