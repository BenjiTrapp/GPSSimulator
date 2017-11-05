package faultInjection;

import communication.communication_jammer.ComJammer;
import faultInjection.fault_library.modes.BitShiftByteManipulationModes;
import faultInjection.fault_library.modes.ByteManipulationModes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

class ByteManipulationCascadeTest {
    @Mock
    private ComJammer jammer = mock(ComJammer.class);

    @BeforeEach
    void setUp(){
        doNothing().when(jammer).send(anyString());
    }

    @Test
    void shouldBuildCorrectByteManipulationFuncs() {
        // given
        ByteManipulationCascade bmb = new ByteManipulationCascade();
        bmb.addByteManipulation(ByteManipulationModes.OFF_BY_ONE, "4711");
        bmb.addByteManipulation(ByteManipulationModes.RANDOM_BITS, "1337");

        // when
        bmb.sendByteManipulations(jammer);

        // then
        verify(jammer,times(2)).send(anyString());
    }

    @Test
    void shouldBuildCorrectBitShiftManipulationFuncs() {
        // given
        ByteManipulationCascade bmb = new ByteManipulationCascade();
        bmb.addBitShiftManipulation(BitShiftByteManipulationModes.FLIP_BITS, "1337", 21);
        bmb.addBitShiftManipulation(BitShiftByteManipulationModes.FLIP_BITS, "4711", 42);

        // when
        bmb.sendBitShiftManipulations(jammer);

        // then
        verify(jammer,times(2)).send(anyString());
    }

    @Test
    void shouldNeverRunSendStepWhenNothingIsInsertedToTheByteManipulationList() {
        // given
        ByteManipulationCascade bmb = new ByteManipulationCascade();
        bmb.addBitShiftManipulation(BitShiftByteManipulationModes.FLIP_BITS, "1337", 21);

        // when
        assertThrows(AssertionError.class, () -> bmb.sendByteManipulations(jammer));

        // then
        verify(jammer,never()).send(anyString());
    }

    @Test
    void shouldNeverRunSendStepWhenNothingIsInsertedToTheBitShiftManipulationList() {
        // given
        ByteManipulationCascade bmb = new ByteManipulationCascade();
        bmb.addByteManipulation(ByteManipulationModes.RANDOM_BITS, "1337");

        // when
        assertThrows(AssertionError.class, () -> bmb.sendBitShiftManipulations(jammer));

        // then
        verify(jammer,never()).send(anyString());
    }

    @Test
    void shouldBuildCorrectBitShiftAndByteManipulationFuncs() {
        // given
        ByteManipulationCascade bmb = new ByteManipulationCascade();
        bmb.addBitShiftManipulation(BitShiftByteManipulationModes.FLIP_BITS, "1337", 21);
        bmb.addBitShiftManipulation(BitShiftByteManipulationModes.FLIP_BITS, "4711", 42);
        bmb.addByteManipulation(ByteManipulationModes.OFF_BY_ONE, "4711");
        bmb.addByteManipulation(ByteManipulationModes.RANDOM_BITS, "1337");

        // when
        bmb.sendBitShiftManipulations(jammer);
        bmb.sendByteManipulations(jammer);

        // then
        verify(jammer,times(4)).send(anyString());
    }

    @Test
    void shouldFaildDueToInvalidArgumentsForBitShifts(){
        // given
        ByteManipulationCascade bmb = new ByteManipulationCascade();

        // when called with null arguments -> then an exception should be thrown
        assertThrows(AssertionError.class, () -> bmb.addBitShiftManipulation(null, "1337", 21));
        assertThrows(AssertionError.class, () -> bmb.addBitShiftManipulation(BitShiftByteManipulationModes.FLIP_BITS, null, 21));
    }

    @Test
    void shouldFaildDueToInvalidArgumentsForByteManipulations(){
        // given
        ByteManipulationCascade bmb = new ByteManipulationCascade();

        // when called with null arguments -> then an exception should be thrown
        assertThrows(AssertionError.class, () -> bmb.addByteManipulation(null, "4711"));
        assertThrows(AssertionError.class, () -> bmb.addByteManipulation(ByteManipulationModes.OFF_BY_ONE, null));
    }
}