package faultInjection.perturbation_functions.byte_manipulation;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OffByOneTest {
    @Test
    void shouldThrowNumberFormatExceptionWhenStringIsInvalid(){
        // given
        final String bullShit = "This Sir, is absolutely Bullshit!";

        // when String is bullshit -> then throw exception
        assertThrows(NumberFormatException.class, () -> new OffByOne(bullShit));
    }

    @Test
    void shouldRemoveOneDueToOddDigit() {
        // given
        final String STRING2PERTURB = "1";
        BytePerturbationFunction OffByOne = new OffByOne(STRING2PERTURB);

        // when
        String result = OffByOne.asString();

        // then
        assertNotEquals(STRING2PERTURB, result);
        assertEquals("0.0", result);
    }

    @Test
    void shouldAddOneDueToEvenDigit() {
        // given
        final String STRING2PERTURB = "2";
        BytePerturbationFunction OffByOne = new OffByOne(STRING2PERTURB);

        // when
        String result = OffByOne.asString();

        // then
        assertNotEquals(STRING2PERTURB, result);
        assertEquals("3.0", result);
    }

    @Test
    void shouldOffByOneAndReturnAsStringWhenDigitIsPassedAsString() {
        // given
        final String STRING2PERTURB = "42";
        BytePerturbationFunction OffByOne = new OffByOne(STRING2PERTURB);

        // when
        String result = OffByOne.asString();

        // then
        assertNotEquals(STRING2PERTURB, result);
        assertEquals("43.0", result);
    }

    @Test
    void shouldOffByOneAndReturnAsStringWhenDigitIsPassedAsDouble() {
        // given
        final Double DOUBLE2PERTURB = 42.0;
        BytePerturbationFunction OffByOne = new OffByOne(DOUBLE2PERTURB);

        // when
        String result = OffByOne.asString();

        // then
        assertNotEquals(DOUBLE2PERTURB, result);
        assertEquals("43.0", result);
    }

    @Test
    void shouldOffByOneAndReturnAsDoubleWhenDigitIsPassedAsDouble() {
        // given
        final Double DOUBLE2PERTURB = 42.0;
        BytePerturbationFunction OffByOne = new OffByOne(DOUBLE2PERTURB);

        // when
        Double result = OffByOne.asDouble();

        // then
        assertNotEquals(DOUBLE2PERTURB, result);
        assertEquals(new Double("43"), result);
    }

    @Test
    void shouldOffByOneAndReturnAsLongWhenDigitIsPassedAsDouble() {
        // given
        final Double DOUBLE2PERTURB = 42.0;
        BytePerturbationFunction OffByOne = new OffByOne(DOUBLE2PERTURB);

        // when
        long result = OffByOne.asLong();

        // then
        assertNotEquals(DOUBLE2PERTURB, result);
        assertEquals(43L, result);
    }    
}