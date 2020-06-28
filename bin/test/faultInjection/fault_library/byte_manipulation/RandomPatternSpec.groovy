package faultInjection.fault_library.byte_manipulation

import spock.lang.Specification


class RandomPatternSpec extends Specification {

    def shouldThrowNumberFormatExceptionWhenStringIsInvalid() {
        given:
        final def bullShit = "This Sir != is absolutely Bullshit!"

        when:
        new RandomPattern(bullShit)

        then:
        thrown(NumberFormatException)
    }


    def shouldRandomPatternAndReturnAsStringWhenDigitIsPassedAsString() {
        given:
        final def STRING2PERTURB = "4711"
        def randomPattern = new RandomPattern(STRING2PERTURB)

        when:
        def result = randomPattern.asString()

        then:
        STRING2PERTURB != result
    }


    def shouldRandomPatternAndReturnAsStringWhenDigitIsPassedAsDouble() {
        given:
        final def DOUBLE2PERTURB = 4711.0
        def randomPattern = new RandomPattern(DOUBLE2PERTURB as Double)

        when:
        def result = randomPattern.asString()

        then:
        DOUBLE2PERTURB != result
    }


    def shouldRandomPatternAndReturnAsDoubleWhenDigitIsPassedAsDouble() {
        given:
        final def DOUBLE2PERTURB = 4711.0
        def randomPattern = new RandomPattern(DOUBLE2PERTURB as Double)

        when:
        def result = randomPattern.asDouble()

        then:
        DOUBLE2PERTURB != result
    }


    def shouldRandomPatternAndReturnAsLongWhenDigitIsPassedAsDouble() {
        given:
        final def DOUBLE2PERTURB = 4711.0
        def randomPattern = new RandomPattern(DOUBLE2PERTURB as Double)

        when:
        long result = randomPattern.asLong()

        then:
        DOUBLE2PERTURB != result
    }

}
