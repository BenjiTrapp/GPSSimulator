package faultInjection.fault_library.down_counter;

@FunctionalInterface
public interface CallbackEvent {
    void notifyToPerturb();
}
