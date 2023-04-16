package training360.school.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;
import java.util.Objects;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SchoolNotFoundExceptions.class)
    public ProblemDetail handleValidationException(SchoolNotFoundExceptions e){
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
        detail.setType(URI.create("schools/school_is_not_found"));
        return detail;}

    @ExceptionHandler(StudentNotFoundException.class)
    public ProblemDetail handleValidationException(StudentNotFoundException e){
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
        detail.setType(URI.create("schools/student_is_not_found"));
        return detail;}

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidationException(MethodArgumentNotValidException e){
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
        detail.setType(URI.create("schools/invalid_request"));
        detail.setDetail(Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage());
        return detail;}
}
