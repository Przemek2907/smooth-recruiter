package com.app.usersservice.validator;

import java.util.Map;

public interface Validator<T> {
    Map<String, String> validate(T item);
    default boolean isUsernameCorrect(String name) {
        return name != null && name.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
    }
}
