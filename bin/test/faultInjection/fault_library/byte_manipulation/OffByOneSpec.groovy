package faultInjection.fault_library.byte_manipulation

import spock.lang.Specification

class OffByOneSpec extends Specification {

    def "Should throw NumberFormatException when string is invalid"() {
        given:
        final def bullShit = "This Sir == is absolutely Bullshit!"

        when:
        new OffByOne(bullShit)

        then:
        thrown(NumberFormatException)
    }


    def "Should remove one due to odd digit"() {
        given:
        final def STRING2PERTURB = "1"
        BytePerturbationFunction OffByOne = new OffByOne(STRING2PERTURB)

        when:
        def result = OffByOne.asString()

        then:
        STRING2PERTURB != result
        "0.0" == result
    }


    def "shouldAddOneDueToEvenDigit"() {
        given:
        final def STRING2PERTURB = "2"
        BytePerturbationFunction OffByOne = new OffByOne(STRING2PERTURB)

        when:
        def result = OffByOne.asString()

        then:
        STRING2PERTURB != result
        "3.0" == result
    }


    def shouldOffByOneAndReturnAsStringWhenDigitIsPassedAsString() {
        given:
        final def STRING2PERTURB = "42"
        BytePerturbationFunction OffByOne = new OffByOne(STRING2PERTURB)

        when:
        def result = OffByOne.asString()

        then:
        STRING2PERTURB != result
        "43.0" == result
    }


    def shouldOffByOneAndReturnAsStringWhenDigitIsPassedAsDouble() {
        given:
        final Double DOUBLE2PERTURB = 42.0
        def OffByOne = new OffByOne(DOUBLE2PERTURB)

        when:
        def result = OffByOne.asString()

        then:
        DOUBLE2PERTURB != result
        "43.0" == result
    }


    def shouldOffByOneAndReturnAsDoubleWhenDigitIsPassedAsDouble() {
        given:
        final def DOUBLE2PERTURB = 42.0
        def OffByOne = new OffByOne(DOUBLE2PERTURB as Double)

        when:
        def result = OffByOne.asDouble()

        then:
        DOUBLE2PERTURB != result
        new Double("43") == result
    }


    def shouldOffByOneAndReturnAsLongWhenDigitIsPassedAsDouble() {
        given:
        final def DOUBLE2PERTURB = 42.0
        def OffByOne = new OffByOne(DOUBLE2PERTURB as Double)

        when:
        def result = OffByOne.asLong()

        then:
        DOUBLE2PERTURB != result
        43L == result
    }
}
