package fr.formation.taskmanager.exception;

public abstract class AppException extends RuntimeException {

    protected AppException(String msg) {
        super(msg);
    }

    protected AppException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public abstract String code(); // ex: "TASK_NOT_FOUND"

    //on distingue les deux type d'exception, ça permet par exemple a l'utilisateur de savoir si l'erreur viens de lui (bad request) ou du service (technical exception)
}

