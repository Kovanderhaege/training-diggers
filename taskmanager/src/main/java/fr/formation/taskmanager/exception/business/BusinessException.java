package fr.formation.taskmanager.exception.business;

import fr.formation.taskmanager.exception.AppException;

public abstract class BusinessException extends AppException {

    protected BusinessException(String msg) {
        super(msg);
    }

    protected BusinessException(String msg, Throwable cause) {
        super(msg, cause);
    }
}