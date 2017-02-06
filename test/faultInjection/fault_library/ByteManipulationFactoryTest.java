package faultInjection.fault_library;

import faultInjection.fault_library.byte_manipulation.BytePerturbationFunction;
import faultInjection.fault_library.byte_manipulation.FlipBits;
import faultInjection.fault_library.byte_manipulation.RandomPattern;
import faultInjection.fault_library.modes.BitShiftByteManipulationModes;
import faultInjection.fault_library.modes.ByteManipulationModes;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ByteManipulationFactoryTest {

    @Test
    void shouldCreateByteManipulationFuncAsString() {
        // given
        ByteManipulationFactory factory = new ByteManipulationFactory();

        // when
        BytePerturbationFunction result = factory.getByteManipulation(ByteManipulationModes.RANDOM_BITS, "4711");

        // then
        assertTrue(result instanceof  RandomPattern);
        assertNotEquals("4711", result.asString());
        assertNotEquals("4711", result.asDouble());
        assertNotEquals("4711", result.asLong());
    }

    @Test
    void shouldCreateByteManipulationFuncAsDouble() {
        // given
        ByteManipulationFactory factory = new ByteManipulationFactory();

        // when
        BytePerturbationFunction result = factory.getByteManipulation(ByteManipulationModes.RANDOM_BITS, new Double(4711));

        // then
        assertTrue(result instanceof  RandomPattern);
        assertNotEquals("4711", result.asString());
        assertNotEquals("4711", result.asDouble());
        assertNotEquals("4711", result.asLong());
    }

    @Test
    void shouldCreateBitShiftManipulationFunc() {
        // given
        ByteManipulationFactory factory = new ByteManipulationFactory();

        // when
        BytePerturbationFunction result = factory.getBitShiftByteManipulation(BitShiftByteManipulationModes.FLIP_BITS, "4711", 2);

        System.out.println(result.asString());

        // then
        assertTrue(result instanceof FlipBits);
        assertNotEquals("4711", result.asString());
        assertNotEquals("4711", result.asDouble());
        assertNotEquals("4711", result.asLong());
    }

    @Test
    void shouldThrowAssertionErrorWhenArgumentIsNullForStringBitShiftManipulations() {
        // given
        ByteManipulationFactory factory = new ByteManipulationFactory();

        // when argument is null -> then an exception is assumed to be thrown
        assertThrows(AssertionError.class, () -> factory.getBitShiftByteManipulation(BitShiftByteManipulationModes.FLIP_BITS, null, 2));
        assertThrows(AssertionError.class, () -> factory.getBitShiftByteManipulation(null, "bla", 2));
        assertThrows(AssertionError.class, () -> factory.getBitShiftByteManipulation(BitShiftByteManipulationModes.FLIP_BITS, "", 2));
    }

    @Test
    void shouldThrowAssertionErrorWhenArgumentIsNullForStringByteManipulations() {
        // given
        ByteManipulationFactory factory = new ByteManipulationFactory();

        // when argument is null -> then an exception is assumed to be thrown
        assertThrows(AssertionError.class, () -> factory.getByteManipulation(ByteManipulationModes.TOGGLE_BITS, (String) null));
        assertThrows(AssertionError.class, () -> factory.getByteManipulation(null, "bla"));
        assertThrows(AssertionError.class, () -> factory.getByteManipulation(ByteManipulationModes.TOGGLE_BITS, ""));
    }

    @Test
    void shouldThrowAssertionErrorWhenArgumentIsNullForDoubleByteManipulations() {
        // given
        ByteManipulationFactory factory = new ByteManipulationFactory();

        // when argument is null -> then an exception is assumed to be thrown
        assertThrows(AssertionError.class, () -> factory.getByteManipulation(ByteManipulationModes.TOGGLE_BITS, (Double) null));
        assertThrows(AssertionError.class, () -> factory.getByteManipulation(null, 42.0));
    }
}