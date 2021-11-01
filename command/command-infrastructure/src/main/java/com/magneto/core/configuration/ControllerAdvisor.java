package com.magneto.core.configuration;

import com.magneto.core.exception.MutantException;
import com.magneto.core.exception.NoMutantException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER_ERROR = LoggerFactory.getLogger(ControllerAdvisor.class);

    @ExceptionHandler(MutantException.class)
    public ResponseEntity<String> handleMutantException(MutantException ex) {
        LOGGER_ERROR.error(ex.getClass().getSimpleName(), ex);
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoMutantException.class)
    public ResponseEntity<String> handleNoMutantException(NoMutantException ex) {
        LOGGER_ERROR.error(ex.getClass().getSimpleName(), ex);
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
    }
}
