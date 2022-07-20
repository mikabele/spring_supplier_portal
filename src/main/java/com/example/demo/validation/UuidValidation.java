package com.example.demo.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Constraint(validatedBy = UuidValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface UuidValidation {
  String message() default "{invalid.uuid}";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
