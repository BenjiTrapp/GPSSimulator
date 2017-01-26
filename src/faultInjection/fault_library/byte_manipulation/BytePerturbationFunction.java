package faultInjection.fault_library.byte_manipulation;

public interface BytePerturbationFunction {
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
