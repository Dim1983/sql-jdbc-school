package com.loktionov.validation;

import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ValidatorTest {
    private final Validator validator = new Validator();

    @Test
    void checkIdUserShouldBeThrowIllegalArgumentExceptionIfChooseLessZeroOrMoreHundert() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
                () -> validator.checkChooseUser(101));

        assertThat("Argument should be more zero and less Hundert", is(thrown.getMessage()));
    }

    @Test
    void checkIdUserShouldBeThrowIllegalArgumentExceptionIfChooseLessZeroOrMoreZero() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
                () -> validator.checkChooseUser(-1));

        assertThat("Argument should be more zero and less Hundert", is(thrown.getMessage()));
    }

    @Test
    void checkIdUserShouldBeNotActionWhenArgumentIsValid() {

        assertDoesNotThrow(() -> validator.checkChooseUser(5));
    }
}
