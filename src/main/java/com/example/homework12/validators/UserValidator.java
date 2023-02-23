package com.example.homework12.validators;

import com.example.homework12.dtos.UserDto;
import com.example.homework12.exceptions.ValidationException;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserValidator {
    public void validate(UserDto userDto){
        List<String> errors = new ArrayList<>();
        if (userDto.getUsername().isBlank()){
            errors.add("Введите имя пользователя");
        }
        if (userDto.getPassword().isBlank()){
            errors.add("Введите имя пароль");
        }
        if (userDto.getEmail().isBlank()){
            errors.add("Введите email");
        }
        if (userDto.getUsername().length() < 3){
            errors.add("Длинна имени не должна быть меньше 3 символов");
        }
        if (userDto.getPassword().length() < 3){
            errors.add("Длинна пароля не должна быть меньше 3 символов");
        }
        if (userDto.getEmail().length() < 3){
            errors.add("Длинна email не должна быть меньше 3 символов");
        }
        if (!errors.isEmpty()){
            throw new ValidationException(errors);
        }
    }
}
