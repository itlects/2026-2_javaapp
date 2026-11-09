package edu.week15;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class DatabaseErrorAdvice {
    private static final Logger log = LoggerFactory.getLogger(DatabaseErrorAdvice.class);

    @ExceptionHandler(DataAccessException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public String databaseError(DataAccessException exception) {
        log.error("Database operation failed", exception);
        return "db-error";
    }
}
