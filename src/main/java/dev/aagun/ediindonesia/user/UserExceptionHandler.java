package dev.aagun.ediindonesia.user;

import dev.aagun.ediindonesia.constant.HttpStatusCommon;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestControllerAdvice(basePackages = {"dev.aagun.ediindonesia"})
public class UserExceptionHandler {

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public UserResponse<List<String>> methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();

        return UserResponse.<List<String>>builder()
                .status(HttpStatusCommon.FAILED.getName())
                .message(HttpStatus.Series.CLIENT_ERROR.name().toLowerCase())
                .data(getErrors(result.getFieldErrors()))
                .build();
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public UserResponse<List<String>> methodArgumentNotValidException(ConstraintViolationException ex) {
        Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();

        String errorMessage = violations.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(", "));

        return UserResponse.<List<String>>builder()
                .status(HttpStatusCommon.FAILED.getName())
                .message(errorMessage)
                .build();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public UserResponse<List<String>> clientException(IllegalArgumentException ex) {
        return UserResponse.<List<String>>builder()
                .status(HttpStatusCommon.FAILED.getName())
                .message(ex.getMessage())
                .build();
    }

    private static List<String> getErrors(List<FieldError> errors) {
        return errors.stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toList());
    }



}
