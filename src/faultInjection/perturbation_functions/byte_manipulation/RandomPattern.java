package faultInjection.perturbation_functions.byte_manipulation;

import java.util.Random;

public class RandomPattern implements BytePerturbationFunctions {
    private double perturbedByte;

    public RandomPattern(double var) {
        perturbedByte =  new Random().nextInt((int) var >> 1);
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
