package faultInjection.fault_library.byte_manipulation

import spock.lang.Specification

class FlipBitsSpec extends Specification {

    def "Should throw NumberFormatException when string is invalid"() {
        given:
        final String bullShit = "This Sir, is absolutely Bullshit!"

        when:
        new FlipBits(bullShit, 1337)

        then:
        thrown(NumberFormatException)
    }


    def "shouldFlipBitsAndReturnAsStringWhenDigitIsPassedAsString"() {
        given:
        final String STRING2PERTURB = "42"
        def flipBits = new FlipBits(STRING2PERTURB, 2)

        when:
        String result = flipBits.asString()

        then:
        STRING2PERTURB != result
        "4.6311077918204232E18" == result
    }


    def shouldFlipBitsAndReturnAsStringWhenDigitIsPassedAsDouble() {
        given:
        final Double DOUBLE2PERTURB = 42.0
        def flipBits = new FlipBits(DOUBLE2PERTURB, 2)

        when:
        String result = flipBits.asString()

        then:
        DOUBLE2PERTURB != result
        "4.6311077918204232E18" == result
    }


    def shouldFlipBitsAndReturnAsDoubleWhenDigitIsPassedAsDouble() {
        given:
        final Double DOUBLE2PERTURB = 42.0
        def flipBits = new FlipBits(DOUBLE2PERTURB, 2)

        when:
        Double result = flipBits.asDouble()

        then:
        DOUBLE2PERTURB != result
        new Double("4.6311077918204232E18") == result
    }


    def shouldFlipBitsAndReturnAsLongWhenDigitIsPassedAsDouble() {
        given:
        final Double DOUBLE2PERTURB = 42.0
        def flipBits = new FlipBits(DOUBLE2PERTURB, 2)

        when:
        long result = flipBits.asLong()

        then:
        DOUBLE2PERTURB != result
        4631107791820423168L == result
    }
}
