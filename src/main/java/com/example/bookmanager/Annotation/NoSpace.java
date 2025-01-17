package com.example.bookmanager.Annotation;

import com.example.bookmanager.Utils.NoSpaceValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = NoSpaceValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface NoSpace {
    String message() default "Space is not allowed here";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
