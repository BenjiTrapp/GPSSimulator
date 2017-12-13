package faultInjection.fault_library.byte_manipulation

import spock.lang.Specification

class ToggleBitsSpec extends Specification {

    def shouldThrowNumberFormatExceptionWhenStringIsInvalid() {
        given:
        final String bullShit = "This Sir, is absolutely Bullshit!"

        when:
        new ToggleBits(bullShit)

        then:
        thrown(NumberFormatException)
    }


    def shouldToggleBitsAndReturnAsStringWhenDigitIsPassedAsString() {
        given:
        final String STRING2PERTURB = "42"
        BytePerturbationFunction toggleBits = new ToggleBits(STRING2PERTURB)

        when:
        String result = toggleBits.asString()

        then:
        STRING2PERTURB != result
        "-4.6311077918204232E18" == result
    }


    def shouldToggleBitsAndReturnAsStringWhenDigitIsPassedAsDouble() {
        given:
        final Double DOUBLE2PERTURB = 42.0
        BytePerturbationFunction toggleBits = new ToggleBits(DOUBLE2PERTURB)

        when:
        String result = toggleBits.asString()

        then:
        DOUBLE2PERTURB != result
        "-4.6311077918204232E18" == result
    }


    def shouldToggleBitsAndReturnAsDoubleWhenDigitIsPassedAsDouble() {
        given:
        final Double DOUBLE2PERTURB = 42.0
        BytePerturbationFunction toggleBits = new ToggleBits(DOUBLE2PERTURB)

        when:
        Double result = toggleBits.asDouble()

        then:
        DOUBLE2PERTURB != result
        new Double("-4.6311077918204232E18") == result
    }


    def shouldToggleBitsAndReturnAsLongWhenDigitIsPassedAsDouble() {
        given:
        final Double DOUBLE2PERTURB = 42.0
        BytePerturbationFunction toggleBits = new ToggleBits(DOUBLE2PERTURB)

        when:
        long result = toggleBits.asLong()

        then:
        DOUBLE2PERTURB != result
        -4631107791820423168L == result
    }

}
