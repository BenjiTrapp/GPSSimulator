package faultInjection.perturbation_functions.down_counter;

@FunctionalInterface
public interface CallbackEvent {
    void notifyToPerturb();
}
