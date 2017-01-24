package faultInjection.perturbation_functions.byte_manipulation;

public class OffByOne implements BytePerturbationFunctions {
    private double perturbedByte;

    public OffByOne(Double var) {
        perturbedByte = (var % 2 == 0) ? ++var : --var;
    }

    @Override
    public double asDouble() {return this.perturbedByte;}

    @Override
    public String asString() {
        return Double.toString(perturbedByte);
    }

    @Override
    public long asLong() {
        return (long) perturbedByte;
    }
}
