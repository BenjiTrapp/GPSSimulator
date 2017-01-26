package faultInjection.perturbation_functions.byte_manipulation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class FlipBitsTest {
    @Test
    void shouldThrowNumberFormatExceptionWhenStringIsInvalid(){
        // given
        final String bullShit = "This Sir, is absolutely Bullshit!";

        // when String is bullshit -> then throw exception
        assertThrows(NumberFormatException.class, () -> new FlipBits(bullShit, 1337));
    }

    @Test
    void shouldFlipBitsAndReturnAsStringWhenDigitIsPassedAsString() {
        // given
        final String STRING2PERTURB = "42";
        BytePerturbationFunction flipBits = new FlipBits(STRING2PERTURB, 2);

        // when
        String result = flipBits.asString();

        // then
        assertNotEquals(STRING2PERTURB, result);
        assertEquals("4.6311077918204232E18", result);
    }

    @Test
    void shouldFlipBitsAndReturnAsStringWhenDigitIsPassedAsDouble() {
        // given
        final Double DOUBLE2PERTURB = 42.0;
        BytePerturbationFunction flipBits = new FlipBits(DOUBLE2PERTURB, 2);

        // when
        String result = flipBits.asString();

        // then
        assertNotEquals(DOUBLE2PERTURB, result);
        assertEquals("4.6311077918204232E18", result);
    }

    @Test
    void shouldFlipBitsAndReturnAsDoubleWhenDigitIsPassedAsDouble() {
        // given
        final Double DOUBLE2PERTURB = 42.0;
        BytePerturbationFunction flipBits = new FlipBits(DOUBLE2PERTURB, 2);

        // when
        Double result = flipBits.asDouble();

        // then
        assertNotEquals(DOUBLE2PERTURB, result);
        assertEquals(new Double("4.6311077918204232E18"), result);
    }

    @Test
    void shouldFlipBitsAndReturnAsLongWhenDigitIsPassedAsDouble() {
        // given
        final Double DOUBLE2PERTURB = 42.0;
        BytePerturbationFunction flipBits = new FlipBits(DOUBLE2PERTURB, 2);

        // when
        long result = flipBits.asLong();

        // then
        assertNotEquals(DOUBLE2PERTURB, result);
        assertEquals(4631107791820423168L, result);
    }
}