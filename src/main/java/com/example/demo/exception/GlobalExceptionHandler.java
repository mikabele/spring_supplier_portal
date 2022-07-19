package com.example.demo.exception;

import com.example.demo.dto.ExceptionDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@Log4j2
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(NotFoundException.class)
  @ResponseBody
  protected ExceptionDto handleNotFoundException(NotFoundException ex) {
    log.warn(ex.getMessage());
    return new ExceptionDto(ex.getMessage(), ex.getMessage(), HttpStatus.NOT_FOUND.value());
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MappingException.class)
  @ResponseBody
  protected ExceptionDto handleMappingException(MappingException ex) {
    log.warn(ex.getMessage());
    return new ExceptionDto(ex.getMessage(), ex.getMessage(), HttpStatus.BAD_REQUEST.value());
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(AlreadyExistsException.class)
  @ResponseBody
  protected ExceptionDto handleAlreadyExistsException(AlreadyExistsException ex) {
    log.warn(ex.getMessage());
    return new ExceptionDto(ex.getMessage(), ex.getMessage(), HttpStatus.BAD_REQUEST.value());
  }

  @Override
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {
    Map<String, ExceptionDto> errors = new HashMap<>();
    ex.getBindingResult()
        .getAllErrors()
        .forEach(
            error -> {
              String fieldName = ((FieldError) error).getField();
              errors.put(
                  fieldName,
                  new ExceptionDto(
                      error.getDefaultMessage(),
                      "Method argument \""
                          + ((FieldError) error).getRejectedValue()
                          + "\" is not valid",
                      HttpStatus.BAD_REQUEST.value()));
            });
    return handleExceptionInternal(ex, errors, headers, HttpStatus.BAD_REQUEST, request);
  }
}
