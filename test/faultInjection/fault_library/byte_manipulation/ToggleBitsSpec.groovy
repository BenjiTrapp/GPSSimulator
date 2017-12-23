package faultInjection.fault_library.byte_manipulation

import spock.lang.Specification

class ToggleBitsSpec extends Specification {

    def shouldThrowNumberFormatExceptionWhenStringIsInvalid() {
        given:
        final def bullShit = "This Sir, is absolutely Bullshit!"

        when:
        new ToggleBits(bullShit)

        then:
        thrown(NumberFormatException)
    }


    def shouldToggleBitsAndReturnAsStringWhenDigitIsPassedAsString() {
        given:
        final def STRING2PERTURB = "42"
        def toggleBits = new ToggleBits(STRING2PERTURB)

        when:
        def result = toggleBits.asString()

        then:
        STRING2PERTURB != result
        "-4.6311077918204232E18" == result
    }


    def shouldToggleBitsAndReturnAsStringWhenDigitIsPassedAsDouble() {
        given:
        final def DOUBLE2PERTURB = 42.0
        def toggleBits = new ToggleBits(DOUBLE2PERTURB as Double)

        when:
        def result = toggleBits.asString()

        then:
        DOUBLE2PERTURB != result
        "-4.6311077918204232E18" == result
    }


    def shouldToggleBitsAndReturnAsDoubleWhenDigitIsPassedAsDouble() {
        given:
        final def DOUBLE2PERTURB = 42.0
        def toggleBits = new ToggleBits(DOUBLE2PERTURB as Double)

        when:
        def result = toggleBits.asDouble()

        then:
        DOUBLE2PERTURB != result
        "-4.6311077918204232E18" as Double == result
    }


    def shouldToggleBitsAndReturnAsLongWhenDigitIsPassedAsDouble() {
        given:
        final def DOUBLE2PERTURB = 42.0
        def toggleBits = new ToggleBits(DOUBLE2PERTURB as Double)

        when:
        long result = toggleBits.asLong()

        then:
        DOUBLE2PERTURB != result
        -4631107791820423168L == result
    }

}
