package faultInjection.perturbation_functions.byte_manipulation;

import java.util.Random;

public class RandomPattern implements BytePerturbationFunctions {
    private double perturbedByte;

    public RandomPattern(double var) {
        Random r = new Random();
        int tmpVar = (int) var;
        perturbedByte = r.nextInt(tmpVar >> 1);
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
