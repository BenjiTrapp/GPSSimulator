package faultInjection;

import communication.communication_jammer.ComJammer;
import faultInjection.fault_library.ByteManipulationFactory;
import faultInjection.fault_library.byte_manipulation.BytePerturbationFunction;
import faultInjection.fault_library.modes.BitShiftByteManipulationModes;
import faultInjection.fault_library.modes.ByteManipulationModes;

import java.util.ArrayList;
import java.util.List;

class ByteManipulationBuilder {
    private List<ByteManipulationHolder> byteManipulationrList = new ArrayList<>();
    private List<BitShiftManipulationHolder> bitShiftList = new ArrayList<>();

    ByteManipulationBuilder addBitShiftManipulation(BitShiftByteManipulationModes mode, String var, Integer shift) {
        bitShiftList.add(new BitShiftManipulationHolder(mode, var, shift));
        return this;
    }

    ByteManipulationBuilder addByteManipulation(ByteManipulationModes mode, String var) {
        byteManipulationrList.add(new ByteManipulationHolder(mode, var));
        return this;
    }

    void sendBitShiftManipulations(ComJammer jammerInstance){
        assert !bitShiftList.isEmpty();
        bitShiftList.forEach( holder -> jammerInstance.send(holder.getBytePerturbationFunction().asString()));
    }

    void sendByteManipulations(ComJammer jammerInstance){
        assert !byteManipulationrList.isEmpty();
        byteManipulationrList.forEach( holder -> jammerInstance.send(holder.getBytePerturbationFunction().asString()));
    }

    /*
     * The following Classes are used to store all information and reduce complexity through encapsulation
     *  => May make a validator obsolete ...
     */

    private class BitShiftManipulationHolder {
        private BitShiftByteManipulationModes mode;
        private String var;
        private Integer shift;

        BitShiftManipulationHolder(BitShiftByteManipulationModes mode, String var, Integer shift){
            assert mode != null;
            assert var != null;
            assert shift != null;

            this.mode = mode;
            this.var = var;
            this.shift = shift;
        }

        BytePerturbationFunction getBytePerturbationFunction(){
            return new ByteManipulationFactory().getBitShiftByteManipulation(mode,var,shift);
        }
    }

    private class ByteManipulationHolder {
        private ByteManipulationModes mode;
        private String var;

        ByteManipulationHolder(ByteManipulationModes mode, String var){
            assert mode != null;
            assert var != null;

            this.mode = mode;
            this.var = var;
        }

        BytePerturbationFunction getBytePerturbationFunction(){
            return new ByteManipulationFactory().getByteManipulation(mode,var);
        }
    }
}
