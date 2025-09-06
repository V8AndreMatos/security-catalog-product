package com.security.catalog.product.services.validation;

import java.util.ArrayList;
import java.util.List;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


import com.security.catalog.product.dto.UserInsertDTO;
import org.springframework.beans.factory.annotation.Autowired;

import com.security.catalog.product.dto.UserInsertDTO;
import com.security.catalog.product.entities.User;
import com.security.catalog.product.repositories.UserRepository;
import com.security.catalog.product.exceptions.FieldMessage;

public class UserInsertValidator implements ConstraintValidator<UserInsertValid, UserInsertDTO> {

    @Autowired
    private UserRepository userRepository;


    @Override
    public void initialize(UserInsertValid ann) {
    }

    @Override
    public boolean isValid(UserInsertDTO dto, ConstraintValidatorContext context) {

        List<FieldMessage> list = new ArrayList<>();

        // Put your validation tests here, adding FieldMessage objects to the list

        User user = userRepository.findByEmail(dto.getEmail());
        if (user != null){
            list.add(new FieldMessage("email" , "Email already exist"));
        }

        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
                    .addConstraintViolation();
        }
        return list.isEmpty();
    }
}