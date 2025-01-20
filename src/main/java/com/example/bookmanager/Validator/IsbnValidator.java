package com.example.bookmanager.Validator;

import com.example.bookmanager.Annotation.IsbnValid;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class IsbnValidator implements ConstraintValidator<IsbnValid, String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null || s.length() != 17) return false;
        String isbn = s.replace("-", "");
        int sum = 0;
        for (int i = 0; i < 12; i++) {
            char c = isbn.charAt(i);
            if (!Character.isDigit(c)) return false;
            int digit = c - '0';
            sum += (i % 2 == 0) ? digit : digit * 3;
        }
        char last = isbn.charAt(12);
        if (!Character.isDigit(last)) return false;
        int checkDigit = last - '0';
        return (10 - (sum % 10)) % 10 == checkDigit;
    }
}
