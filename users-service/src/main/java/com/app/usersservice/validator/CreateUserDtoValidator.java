package com.app.usersservice.validator;

import com.app.usersservice.dto.CreateUserDto;
import com.app.usersservice.exceptions.UserServiceException;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CreateUserDtoValidator implements Validator<CreateUserDto> {

    @Override
    public Map<String, String> validate(CreateUserDto createUserDto) {
        var errors = new HashMap<String, String>();

        if (createUserDto == null) {
            errors.put("Object", "is null");
        }

        if (!isUsernameCorrect(createUserDto.getUserEmail())) {
            errors.put("username", "Username should be in the form of email");
        }

        if (createUserDto.getPassword() == null) {
            throw new UserServiceException("No password provided");
        }

        if (!isPasswordCorrect(createUserDto.getPassword(), createUserDto.getPasswordConfirmation())) {
            errors.put(createUserDto.getPassword(), "PASSWORDS are not the same");
        }

        return errors;
    }

    private boolean isUsernameCorrect(String name) {
        return name != null && name.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
    }

    private boolean isPasswordCorrect(String pwd, String pwdConfirmation) {
        return Objects.equals(pwd, pwdConfirmation);
    }

}
