package faultInjection.perturbation_functions.byte_manipulation;

import java.util.Random;

public class RandomPattern implements BytePerturbationFunction {
    private double perturbedByte;

    public RandomPattern(Double var) {
        perturbedByte =  new Random().nextInt((int) var.doubleValue() >> 1);
    }

    public RandomPattern(String var) {
        perturbedByte =  new Random().nextInt((int) Double.parseDouble(var) >> 1);
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
