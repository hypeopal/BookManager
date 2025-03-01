package com.example.bookmanager.Annotation;

import com.example.bookmanager.Validator.BookCategoryValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = BookCategoryValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface BookCategoryValid {
    String message() default "Invalid book category";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
