package faultInjection.fault_library.byte_manipulation;

public class FlipBits implements BytePerturbationFunction {
    private double perturbedByte;

    public FlipBits(String var, int bits) {
        double varD = Double.parseDouble(var);
        perturbedByte = Double.doubleToRawLongBits(varD) ^ (1 << bits);
    }

    public FlipBits(Double var, int bits) {
        perturbedByte = Double.doubleToRawLongBits(var) ^ (1 << bits);
    }

    @Override
    public double asDouble() {
        return this.perturbedByte;
    }

    @Override
    public String asString() {
        return Double.toString(perturbedByte);
    }

    @Override
    public long asLong() {
        return (long) perturbedByte;
    }
}
