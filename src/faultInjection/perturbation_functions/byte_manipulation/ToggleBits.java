package faultInjection.perturbation_functions.byte_manipulation;

public class ToggleBits implements BytePerturbationFunctions {
    private double perturbedByte;

    public ToggleBits(Double var) {
        perturbedByte = ~Double.doubleToRawLongBits(var);
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
