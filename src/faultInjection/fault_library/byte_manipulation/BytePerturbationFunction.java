package faultInjection.fault_library.byte_manipulation;

public interface BytePerturbationFunction {
    double asDouble();
    String asString();
    long asLong();

    default int allBitsLow() {return 0;}

    default int allBitsHigh() {return  ~0;}
}
