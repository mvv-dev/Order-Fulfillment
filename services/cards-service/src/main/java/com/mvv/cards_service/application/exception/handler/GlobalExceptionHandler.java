package com.mvv.cards_service.application.exception.handler;

import com.mvv.cards_service.application.exception.DuplicateRegisterException;
import com.mvv.cards_service.application.exception.dto.FieldErrorDTO;
import com.mvv.cards_service.application.exception.dto.SimpleError;
import com.mvv.cards_service.application.exception.dto.UnprocessableEntityError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<UnprocessableEntityError> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {

        List<FieldError> fieldErrors = e.getFieldErrors();
        List<FieldErrorDTO> errors = fieldErrors.stream().map(
                fe -> new FieldErrorDTO(fe.getField(), fe.getDefaultMessage())
        ).toList();

        UnprocessableEntityError unprocessableEntityError= new UnprocessableEntityError(
                HttpStatus.valueOf(422).value(), "Erro de validação nos seguintes campos: ", errors
        );

        return ResponseEntity.status(unprocessableEntityError.status()).body(unprocessableEntityError);


    }

    @ExceptionHandler(DuplicateRegisterException.class)
    public ResponseEntity<SimpleError> handleDuplicateRegisterException(DuplicateRegisterException e) {

        SimpleError simpleError = new SimpleError(HttpStatus.CONFLICT.value(), e.getMessage());
        return ResponseEntity.status(simpleError.status()).body(simpleError);

    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<SimpleError> handleIllegalArgumentException(IllegalArgumentException e) {

        SimpleError simpleError = new SimpleError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        return ResponseEntity.badRequest().body(simpleError);


    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<SimpleError> handleDefaultErrors(Exception e) {

        log.error("Unhandled Exception", e);
        SimpleError simpleError = new SimpleError(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "An unexpected error occurred. Please contact the administration");

        return ResponseEntity.internalServerError().body(simpleError);

    }





}
