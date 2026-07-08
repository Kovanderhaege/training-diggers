package fr.formation.taskmanager.model.task;

import fr.formation.taskmanager.model.TaskId;

import java.time.LocalDate;
import java.util.Objects;

public abstract class AbstractTask {

	/**
	 * Le constructeur devrait être protected (une classe abstraite ne s'instancie
	 * jamais directement, donc public n'a pas de sens ici).
	 * 
	 * Les champs devraient être protected plutôt que private, sinon les sous-classes
	 * n'ont accès qu'aux getters, pas aux attributs eux-mêmes.
	 */
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

    
    /**
     * ATTENTION : equals() et hashCode() ne sont pas cohérents entre eux.
     * 
     * equals() ne se base que sur l'id, mais hashCode() prend en compte
     * id, title, done et createdAt. Or le contrat Java dit clairement que
     * si deux objets sont equals(), ils doivent avoir le même hashCode().
     * 
     * Ici c'est faux : deux tâches avec le même id peuvent avoir un hashCode
     * différent si on change le title ou le statut done, alors qu'elles sont
     * censées être "la même" tâche.
     * 
     * En pratique ça casse les HashMap/HashSet : on peut perdre un objet,
     * avoir des doublons, ou ne plus le retrouver avec contains()/get().
     * 
     * À corriger : hashCode() doit utiliser les mêmes champs que equals(),
     * donc seulement l'id ici -> Objects.hash(id)
     */
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
