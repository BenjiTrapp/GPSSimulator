package faultInjection.fault_library.byte_manipulation;

public class OffByOne implements BytePerturbationFunction {
    private double perturbedByte;

    public OffByOne(Double var) {
        perturbedByte = (var % 2 == 0) ? ++var : --var;
    }

    public OffByOne(String var) {this(Double.parseDouble(var));}

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
