package faultInjection.fault_library.byte_manipulation

import spock.lang.Specification


class BytePerturbationFunctionsSpec extends Specification {
    def "Should set all bits high with default method from interface"() {
        given:
        def bytePerturbationFunction = new RandomPattern(123 as Double)

        expect:
        -1 == bytePerturbationFunction.allBitsHigh()
    }

    def "Should set all bits low with default method from interface"() {
        given:
        def bytePerturbationFunction = new RandomPattern(123 as Double)

        expect:
        0 == bytePerturbationFunction.allBitsLow()
    }
}