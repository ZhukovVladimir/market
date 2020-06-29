package ru.reksoft.market.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.reksoft.market.controller.api.ErrorResponse;
import ru.reksoft.market.exception.InternalServerErrorException;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        List<String> response = new ArrayList<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            response.add("\n" + fieldError.getDefaultMessage());
        }

        return new ResponseEntity<>(new ErrorResponse(response), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InternalServerErrorException.class)
    protected ResponseEntity<Object> handleInternalServerErrorException(RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "Произошла непредвиденная ошибка";
        return new ResponseEntity<>(new ErrorResponse(bodyOfResponse), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
