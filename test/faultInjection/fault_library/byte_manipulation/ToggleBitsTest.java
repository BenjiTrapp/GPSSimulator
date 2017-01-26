package faultInjection.fault_library.byte_manipulation;


import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ToggleBitsTest {
    @Test
    void shouldThrowNumberFormatExceptionWhenStringIsInvalid(){
        // given
        final String bullShit = "This Sir, is absolutely Bullshit!";

        // when String is bullshit -> then throw exception
        assertThrows(NumberFormatException.class, () -> new ToggleBits(bullShit));
    }

    @Test
    void shouldToggleBitsAndReturnAsStringWhenDigitIsPassedAsString() {
        // given
        final String STRING2PERTURB = "42";
        BytePerturbationFunction toggleBits = new ToggleBits(STRING2PERTURB);

        // when
        String result = toggleBits.asString();

        // then
        assertNotEquals(STRING2PERTURB, result);
        System.out.println(result);
        assertEquals("-4.6311077918204232E18", result);
    }

    @Test
    void shouldToggleBitsAndReturnAsStringWhenDigitIsPassedAsDouble() {
        // given
        final Double DOUBLE2PERTURB = 42.0;
        BytePerturbationFunction toggleBits = new ToggleBits(DOUBLE2PERTURB);

        // when
        String result = toggleBits.asString();

        // then
        assertNotEquals(DOUBLE2PERTURB, result);
        System.out.println(result);
        assertEquals("-4.6311077918204232E18", result);
    }

    @Test
    void shouldToggleBitsAndReturnAsDoubleWhenDigitIsPassedAsDouble() {
        // given
        final Double DOUBLE2PERTURB = 42.0;
        BytePerturbationFunction toggleBits = new ToggleBits(DOUBLE2PERTURB);

        // when
        Double result = toggleBits.asDouble();

        // then
        assertNotEquals(DOUBLE2PERTURB, result);
        System.out.println(toggleBits);
        assertEquals(new Double("-4.6311077918204232E18"), result);
    }

    @Test
    void shouldToggleBitsAndReturnAsLongWhenDigitIsPassedAsDouble() {
        // given
        final Double DOUBLE2PERTURB = 42.0;
        BytePerturbationFunction toggleBits = new ToggleBits(DOUBLE2PERTURB);

        // when
        long result = toggleBits.asLong();

        // then
        assertNotEquals(DOUBLE2PERTURB, result);
        System.out.println(result);
        assertEquals(-4631107791820423168L, result);
    }

}