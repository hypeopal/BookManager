package com.example.bookmanager.Validator;

import com.example.bookmanager.Annotation.BookCategoryValid;
import com.example.bookmanager.Type.BookCategory;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class BookCategoryValidator implements ConstraintValidator<BookCategoryValid, String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null || s.trim().isEmpty()) return false;
        try {
            BookCategory.valueOf(s);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
