package faultInjection.perturbation_functions.byte_manipulation;

import java.util.Random;

public interface BytePerturbationFunctions {
    double asDouble();
    String asString();
    long asLong();

    default int allBitsLow() {
        return ((int) 0);
    }

    default int allBitsHigh() {
        return ((int) ~0);
    }
}
