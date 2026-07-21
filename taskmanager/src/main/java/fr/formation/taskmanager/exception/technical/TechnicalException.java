package fr.formation.taskmanager.exception.technical;

import fr.formation.taskmanager.exception.AppException;

public abstract class TechnicalException extends AppException {

    protected TechnicalException(String msg) {
        super(msg);
    }

    protected TechnicalException(String msg, Throwable cause) {
        super(msg, cause);
    }
}