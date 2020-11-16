package com.loktionov.validation;

public class Validator {
    public void checkChooseUser(int chooseOfUser) {
        if (chooseOfUser <= 0 || chooseOfUser > 100) {
            throw new IllegalArgumentException("Argument should be more zero and less Hundert");
        }
    }
}
