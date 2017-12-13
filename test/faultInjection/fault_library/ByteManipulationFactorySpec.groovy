package faultInjection.fault_library

import faultInjection.fault_library.byte_manipulation.BytePerturbationFunction
import faultInjection.fault_library.byte_manipulation.FlipBits
import faultInjection.fault_library.byte_manipulation.RandomPattern
import spock.lang.Specification

import static faultInjection.fault_library.modes.BitShiftByteManipulationModes.*
import static faultInjection.fault_library.modes.ByteManipulationModes.*

class ByteManipulationFactorySpec extends Specification {
    def shouldCreateByteManipulationFuncAsString() {
        given:
        ByteManipulationFactory factory = new ByteManipulationFactory()

        when:
        BytePerturbationFunction result = factory.getByteManipulation(RANDOM_BITS, "4711")

        then:
        result instanceof RandomPattern
        "4711" != result.asString()
        "4711" != result.asDouble()
        "4711" != result.asLong()
    }


    def shouldCreateByteManipulationFuncAsDouble() {
        given:
        ByteManipulationFactory factory = new ByteManipulationFactory()

        when:
        BytePerturbationFunction result = factory.getByteManipulation(RANDOM_BITS, new Double(4711))

        then:
        result instanceof RandomPattern
        "4711" != result.asString()
        "4711" != result.asDouble()
        "4711" != result.asLong()
    }


    def shouldCreateBitShiftManipulationFunc() {
        given:
        ByteManipulationFactory factory = new ByteManipulationFactory()

        when:
        BytePerturbationFunction result = factory.getBitShiftByteManipulation(FLIP_BITS, "4711", 2)

        System.out.println(result.asString())

        then:
        result instanceof FlipBits
        "4711" != result.asString()
        "4711" != result.asDouble()
        "4711" != result.asLong()
    }


    def shouldThrowAssertionErrorWhenArgumentIsNullForStringBitShiftManipulations() {
        given:
        ByteManipulationFactory factory = new ByteManipulationFactory()

        when: //argument is null -> then an exception is assumed to be thrown
        factory.getBitShiftByteManipulation(mode, var, bits)

        then:
        thrown(expectation)

        where:
        mode        | var   | bits  || expectation
        null        | "bla" | 2     || AssertionError
        FLIP_BITS   | null  | 2     || AssertionError
        FLIP_BITS   | ""    | 2     || AssertionError
    }


    def shouldThrowAssertionErrorWhenArgumentIsNullForStringByteManipulations() {
        given:
        ByteManipulationFactory factory = new ByteManipulationFactory()

        when: //argument is null -> then an exception is assumed to be thrown
        factory.getByteManipulation(mode, var)

        then:
        thrown(expectation)

        where:
        mode         | var              || expectation
        null         | "bla"            || AssertionError
        TOGGLE_BITS  | null as String   || AssertionError
        TOGGLE_BITS  | ""               || AssertionError
    }


    def shouldThrowAssertionErrorWhenArgumentIsNullForDoubleByteManipulations() {
        given:
        ByteManipulationFactory factory = new ByteManipulationFactory()
        
        when: //argument is null -> then an exception is assumed to be thrown
        factory.getByteManipulation(mode, var)

        then:
        thrown(expectation)

        where:
        mode         | var              || expectation
        null         | 42.0 as Double   || AssertionError
        TOGGLE_BITS  | null as Double   || AssertionError
    }
}
