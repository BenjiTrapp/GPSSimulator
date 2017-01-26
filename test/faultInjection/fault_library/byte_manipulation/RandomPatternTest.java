package faultInjection.fault_library.byte_manipulation;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RandomPatternTest {
    @Test
    void shouldThrowNumberFormatExceptionWhenStringIsInvalid(){
        // given
        final String bullShit = "This Sir, is absolutely Bullshit!";

        // when String is bullshit -> then throw exception
        assertThrows(NumberFormatException.class, () -> new RandomPattern(bullShit));
    }

    @Test
    void shouldRandomPatternAndReturnAsStringWhenDigitIsPassedAsString() {
        // given
        final String STRING2PERTURB = "4711";
        BytePerturbationFunction randomPattern = new RandomPattern(STRING2PERTURB);

        // when
        String result = randomPattern.asString();

        // then
        System.out.println(result);
        assertNotEquals(STRING2PERTURB, result);
    }

    @Test
    void shouldRandomPatternAndReturnAsStringWhenDigitIsPassedAsDouble() {
        // given
        final Double DOUBLE2PERTURB = 4711.0;
        BytePerturbationFunction randomPattern = new RandomPattern(DOUBLE2PERTURB);

        // when
        String result = randomPattern.asString();

        // then
        System.out.println(result);
        assertNotEquals(DOUBLE2PERTURB, result);
    }

    @Test
    void shouldRandomPatternAndReturnAsDoubleWhenDigitIsPassedAsDouble() {
        // given
        final Double DOUBLE2PERTURB = 4711.0;
        BytePerturbationFunction randomPattern = new RandomPattern(DOUBLE2PERTURB);

        // when
        Double result = randomPattern.asDouble();

        // then
        System.out.println(result);
        assertNotEquals(DOUBLE2PERTURB, result);
    }

    @Test
    void shouldRandomPatternAndReturnAsLongWhenDigitIsPassedAsDouble() {
        // given
        final Double DOUBLE2PERTURB = 4711.0;
        BytePerturbationFunction randomPattern = new RandomPattern(DOUBLE2PERTURB);

        // when
        long result = randomPattern.asLong();

        // then
        System.out.println(result);
        assertNotEquals(DOUBLE2PERTURB, result);
    }

}