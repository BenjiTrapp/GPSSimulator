package faultInjection.fault_library.byte_manipulation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BytePerturbationFunctionsTest {
    @Test
    void shouldSetAllBitsHighWithDefaultMethodFromInterface() {
        // given
        BytePerturbationFunction bytePerturbationFunction = new RandomPattern(123.0);

        // when
        int result = bytePerturbationFunction.allBitsHigh();

        // then
        assertEquals(-1, result);
    }

    @Test
    void shouldSetAllBitsLowWithDefaultMethodFromInterface() {
        // given
        BytePerturbationFunction bytePerturbationFunction = new RandomPattern(123.0);

        // when
        int result = bytePerturbationFunction.allBitsLow();

        // then
        assertEquals(0, result);
    }
}