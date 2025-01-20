package com.example.bookmanager.Validator;

import com.example.bookmanager.Annotation.NoSpace;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NoSpaceValidator implements ConstraintValidator<NoSpace, String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext context) {
        if (s != null) return !s.contains(" ");
        return true;
    }
}
