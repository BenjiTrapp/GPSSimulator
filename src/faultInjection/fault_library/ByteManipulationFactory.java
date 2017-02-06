package faultInjection.fault_library;

import faultInjection.fault_library.byte_manipulation.*;
import faultInjection.fault_library.modes.BitShiftByteManipulationModes;
import faultInjection.fault_library.modes.ByteManipulationModes;

import java.util.Objects;

public class ByteManipulationFactory {

    public BytePerturbationFunction getBitShiftByteManipulation(BitShiftByteManipulationModes mode, String var, int bits){
        assert mode != null;
        assert var != null;
        assert !Objects.equals(var, "");

        switch (mode){
            case FLIP_BITS: return new FlipBits(var,bits);
            default: throw new IllegalArgumentException("Mode couldn't be validated");
        }
    }

    public BytePerturbationFunction getByteManipulation(ByteManipulationModes mode, String var) {
        assert mode != null;
        assert var != null;
        assert !Objects.equals(var, "");

        switch (mode){
            case RANDOM_BITS: return new RandomPattern(var);
            case OFF_BY_ONE:  return  new OffByOne(var);
            case TOGGLE_BITS: return new ToggleBits(var);
            default:          throw new IllegalArgumentException("Mode couldn't be validated");
        }
    }


    public BytePerturbationFunction getByteManipulation(ByteManipulationModes mode, Double var) {
        assert mode != null;
        assert var != null;

        switch (mode) {
            case RANDOM_BITS:  return new RandomPattern(var);
            case OFF_BY_ONE:   return new OffByOne(var);
            case TOGGLE_BITS:  return new ToggleBits(var);
            default:           throw new IllegalArgumentException("Mode couldn't be validated");
        }
    }
}
