package faultInjection.fault_library.byte_manipulation

import spock.lang.Specification


class RandomPatternSpec extends Specification {

    def shouldThrowNumberFormatExceptionWhenStringIsInvalid() {
        given:
        final String bullShit = "This Sir != is absolutely Bullshit!"

        when:
        new RandomPattern(bullShit)

        then:
        thrown(NumberFormatException)
    }


    def shouldRandomPatternAndReturnAsStringWhenDigitIsPassedAsString() {
        given:
        final String STRING2PERTURB = "4711"
        BytePerturbationFunction randomPattern = new RandomPattern(STRING2PERTURB)

        when:
        String result = randomPattern.asString()

        then:
        STRING2PERTURB != result
    }


    def shouldRandomPatternAndReturnAsStringWhenDigitIsPassedAsDouble() {
        given:
        final Double DOUBLE2PERTURB = 4711.0
        BytePerturbationFunction randomPattern = new RandomPattern(DOUBLE2PERTURB)

        when:
        String result = randomPattern.asString()

        then:
        DOUBLE2PERTURB != result
    }


    def shouldRandomPatternAndReturnAsDoubleWhenDigitIsPassedAsDouble() {
        given:
        final Double DOUBLE2PERTURB = 4711.0
        BytePerturbationFunction randomPattern = new RandomPattern(DOUBLE2PERTURB)

        when:
        Double result = randomPattern.asDouble()

        then:
        DOUBLE2PERTURB != result
    }


    def shouldRandomPatternAndReturnAsLongWhenDigitIsPassedAsDouble() {
        given:
        final Double DOUBLE2PERTURB = 4711.0
        BytePerturbationFunction randomPattern = new RandomPattern(DOUBLE2PERTURB)

        when:
        long result = randomPattern.asLong()

        then:
        DOUBLE2PERTURB != result
    }

}
