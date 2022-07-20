package com.example.demo.validation;

import lombok.extern.log4j.Log4j2;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Log4j2
public class UuidValidator implements ConstraintValidator<UuidValidation, String> {
  private final String pattern =
      "^[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}$";

  @Override
  public boolean isValid(String uuid, ConstraintValidatorContext constraintValidatorContext) {
    return uuid.matches(pattern);
  }
}
