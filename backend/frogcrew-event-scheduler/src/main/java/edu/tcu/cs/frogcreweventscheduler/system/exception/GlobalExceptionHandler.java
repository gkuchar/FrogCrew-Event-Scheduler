package edu.tcu.cs.frogcreweventscheduler.system.exception;

import edu.tcu.cs.frogcreweventscheduler.system.Result;
import edu.tcu.cs.frogcreweventscheduler.system.StatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * Catches exceptions across controllers and wraps them in the standard Result response.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<Result> handleNotFound(ObjectNotFoundException ex) {
        Result result = new Result(false, StatusCode.NOT_FOUND, ex.getMessage());
        return ResponseEntity.ok(result);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Result> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError fe : ex.getBindingResult().getFieldErrors()) {
            errors.put(fe.getField(), fe.getDefaultMessage());
        }
        Result result = new Result(false, StatusCode.INVALID_ARGUMENT, "Validation failed", errors);
        return ResponseEntity.ok(result);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Result> handleOther(Exception ex) {
        Result result = new Result(false, StatusCode.INTERNAL_SERVER_ERROR, ex.getMessage());
        return ResponseEntity.ok(result);
    }
}
