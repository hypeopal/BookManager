package com.example.bookmanager.Annotation;

import com.example.bookmanager.Validator.IsbnValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = IsbnValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface IsbnValid {
    String message() default "Invalid ISBN";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
