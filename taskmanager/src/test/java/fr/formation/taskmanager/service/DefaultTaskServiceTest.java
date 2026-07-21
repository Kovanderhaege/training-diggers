package fr.formation.taskmanager.service;

import fr.formation.taskmanager.exception.business.InvalidTaskException;
import fr.formation.taskmanager.exception.business.TaskNotFoundException;
import fr.formation.taskmanager.factory.TaskFactory;
import fr.formation.taskmanager.model.Priority;
import fr.formation.taskmanager.model.TaskId;
import fr.formation.taskmanager.model.task.AbstractTask;
import fr.formation.taskmanager.model.task.SimpleTask;
import fr.formation.taskmanager.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DefaultTaskServiceTest {

    @Mock
    private TaskRepository repo;
    @Mock
    private TaskFactory factory;
    @InjectMocks
    private DefaultTaskService service;

    //on peut tester le service seul car on a une architecture en couche

    @Test
    @DisplayName("Doit compléter une tâche existante")
    void shouldCompleteTask() {
// ■■■ Given ■■■ on prépare le comportement du mock
        var task = new SimpleTask(new TaskId(1), "Test", true, LocalDate.now(), Priority.HIGH);
        when(repo.findById(1)).thenReturn(Optional.of(task));
// ■■■ When ■■■ on exécute l'action testée
        service.completeTask(1);
// ■■■ Then ■■■ on vérifie état + interactions (AssertJ)
        assertThat(task.isDone()).isTrue();
        assertThat(task.getTitle()).isEqualTo("Test");
        verify(repo).save(task); // save appelé UNE fois
        verifyNoMoreInteractions(repo); // et rien d'autre
    }

    @Test
    @DisplayName("Doit créer et trouver une tâche")
    void shouldCreateAndFindTask() {
        //Given
        var task = new SimpleTask(new TaskId(1), "Test", true, LocalDate.now(), Priority.HIGH);
        when(repo.findById(1)).thenReturn(Optional.of(task));

        //When
        service.addTask(task);
        AbstractTask result = service.findTask(1);

        //Then
        assertThat(task.equals(result)).isTrue();
        verify(repo).save(task);
        verifyNoMoreInteractions(repo);
    }

    @Test
    @DisplayName("Doit lancer une exception si le titre est vide")
    void shouldThrowWhenTitleIsBlank() {
        //Given
        var task = new SimpleTask(new TaskId(1), "", true, LocalDate.now(), Priority.HIGH);

        //When
        //Then
        assertThrows(InvalidTaskException.class, () -> {service.addTask(task);});
    }

    @Test
    @DisplayName("Doit lancer une exception si la task n'est pas trouvée")
    void shouldThrowWhenTaskNotFound() {
        //Given
        //When
        //Then
        assertThrows(TaskNotFoundException.class, () -> {service.findTask(1);});
    }


    @Nested
    class TestWithMultipleTasks {
        @BeforeEach
        public void setUp() {
            //Given
            var task = new SimpleTask(new TaskId(1), "1", true, LocalDate.now(), Priority.HIGH);
            var task2 = new SimpleTask(new TaskId(2), "2", true, LocalDate.now(), Priority.LOW);
            when(repo.findAll()).thenReturn(List.of(task, task2));
            service.addTask(task);
            service.addTask(task2);
        }

        @Test
        @DisplayName("Doit la liste avec la priorité demandée")
        void shouldFilterByPriority() {
            //When
            List<AbstractTask> result = service.getTasksByPriority(Priority.HIGH);

            //Then
            result.forEach(abstractTask -> {
                if (abstractTask instanceof SimpleTask s) {
                    assertThat(Priority.HIGH.equals(s.getPriority())).isTrue();
                }
            });
        }

        @Test
        @DisplayName("Doit retourner 2 listes cohérentes")
        void shouldPartitionTasksByStatus() {
            Map<Boolean, List<AbstractTask>> result = service.partitionByStatus();

            //Then
            result.get(true).forEach(abstractTask -> assertThat(abstractTask.isDone()).isTrue());
            result.get(false).forEach(abstractTask -> assertThat(abstractTask.isDone()).isFalse());
        }

        @Test
        @DisplayName("Doit retourner uniquement les tâches actives")
        void shouldReturnOnlyActiveTasks() {
            List<AbstractTask> result = service.getActiveTasks();

            //Then
            result.forEach(abstractTask -> assertThat(abstractTask.isDone()).isFalse());
        }
    }
}