package fr.formation.taskmanager.exception.business;

public class InvalidTaskException extends BusinessException {
    private final String field;
    public InvalidTaskException(String field, String reason) {
        super("Champ invalide '" + field + "' : " + reason);
        this.field = field;
    }
    public String code() { return "INVALID_TASK"; }
    public String getField() { return field; }
}