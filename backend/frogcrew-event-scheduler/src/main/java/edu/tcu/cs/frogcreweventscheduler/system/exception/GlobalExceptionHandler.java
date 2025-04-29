package edu.tcu.cs.frogcreweventscheduler.system.exception;

import edu.tcu.cs.frogcreweventscheduler.system.Result;
import edu.tcu.cs.frogcreweventscheduler.system.StatusCode;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    // 1) Domain “not found” exceptions → code 404
    @ExceptionHandler(ObjectNotFoundException.class)
    @ResponseBody
    public Result handleNotFound(ObjectNotFoundException ex) {
        return new Result(false, StatusCode.NOT_FOUND, ex.getMessage(), null);
    }

    // 2) Validation failures → code 400
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public Result handleValidation(MethodArgumentNotValidException ex) {
        String messages = ex.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining("; "));
        return new Result(false, StatusCode.INVALID_ARGUMENT, messages, null);
    }

    // 3) Malformed JSON → also treat as bad request
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public Result handleParseError(HttpMessageNotReadableException ex) {
        return new Result(false, StatusCode.INVALID_ARGUMENT,
                "Malformed request body: " + ex.getMostSpecificCause().getMessage(),
                null);
    }

    // 4) Everything else → code 500
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result handleAllOther(Exception ex) {
        return new Result(false, StatusCode.INTERNAL_SERVER_ERROR,
                "Server error: " + ex.getMessage(),
                null);
    }
}
