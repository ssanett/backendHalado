package org.training360.team.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;
import java.util.Objects;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidationException(MethodArgumentNotValidException e){
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
        detail.setType(URI.create("teams/invalid_request"));
        detail.setDetail(Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage());
        return detail;}

    @ExceptionHandler(PlayerNotFoundException.class)
    public ProblemDetail handleValidationException(PlayerNotFoundException e){
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
        detail.setType(URI.create("examregistration/invalid_request"));
        detail.setDetail(Objects.requireNonNull(e.getMessage()));
        return detail;}

    @ExceptionHandler(TeamNotFoundException.class)
    public ProblemDetail handleValidationException(TeamNotFoundException e){
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
        detail.setType(URI.create("teams/invalid_request"));
        detail.setDetail(Objects.requireNonNull(e.getMessage()));
        return detail;}
}
