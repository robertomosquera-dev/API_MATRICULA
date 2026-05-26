package org.mt.ev.infrastructure.exceptions;

import org.mt.ev.application.exceptions.StudentInvalidStateException;
import org.mt.ev.domain.exceptions.*;
import org.mt.ev.infrastructure.Utils.ApiResponse;
import org.mt.ev.infrastructure.Utils.ApiResponseImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CourseInvalidStateException.class)
    public ResponseEntity<ApiResponse<Void>> handleCourseInvalidState(CourseInvalidStateException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponseImpl.error(400, ex.getMessage()));
    }

    @ExceptionHandler(TuitionInvalidStateException.class)
    public ResponseEntity<ApiResponse<Void>> handleTuitionInvalidState(TuitionInvalidStateException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponseImpl.error(400, ex.getMessage()));
    }

    @ExceptionHandler(TuitionValidationException.class)
    public ResponseEntity<ApiResponse<Void>> handleTuitionValidation(TuitionValidationException ex) {
        return ResponseEntity.status(422)
                .body(ApiResponseImpl.error(422, ex.getMessage()));
    }

    @ExceptionHandler(TuitionDetailValidationException.class)
    public ResponseEntity<ApiResponse<Void>> handleTuitionDetailValidation(TuitionDetailValidationException ex) {
        return ResponseEntity.status(422)
                .body(ApiResponseImpl.error(422, ex.getMessage()));
    }

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<ApiResponse<Void>> handleDomain(DomainException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponseImpl.error(400, ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        field -> field.getDefaultMessage() != null ? field.getDefaultMessage() : "Campo inválido"
                ));

        return ResponseEntity.status(422)
                .body(ApiResponseImpl.error(422, "Error de validación", errors));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGeneric(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponseImpl.error(500, "Error interno del servidor"));
    }

    @ExceptionHandler(CourseNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleCourseNotFound(CourseNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponseImpl.error(404, ex.getMessage()));
    }

    @ExceptionHandler(StudentNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleStudentNotFound(StudentNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponseImpl.error(404, ex.getMessage()));
    }

    @ExceptionHandler(TuitionNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleTuitionNotFound(TuitionNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponseImpl.error(404, ex.getMessage()));
    }

    @ExceptionHandler(StudentInvalidStateException.class)
    public ResponseEntity<ApiResponse<Void>> handleStudentInvalidState(StudentInvalidStateException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponseImpl.error(400, ex.getMessage()));
    }

}