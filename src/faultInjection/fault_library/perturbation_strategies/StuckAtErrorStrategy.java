package faultInjection.fault_library.perturbation_strategies;

import annotations.PerturbationFunction;
import faultInjection.fault_library.modes.PerturbationModes;
import gps.data.GPSData;

@PerturbationFunction(PerturbationModes.STUCK_ERROR)
public final class StuckAtErrorStrategy extends AbstractPerturbationStrategy {

    private volatile int millis = 1000;

    public StuckAtErrorStrategy() {super(PerturbationModes.STUCK_ERROR);}

    public void setStuckTime(int millis){
        assert millis > 0;

        this.millis = millis;
    }

    private void timeToStayStuckAndTurnOff() {
        new Thread(() -> {
            try {
                Thread.sleep(millis);
            } catch (InterruptedException ignored) {
            } finally {this.turnOff();}
        }).start();
    }

    private void turnOff() {GPSData.stuckAtState(false);}

    @Override
    public synchronized void perturb() {
        new Thread(() -> GPSData.stuckAtState(true)).start();
        timeToStayStuckAndTurnOff();
    }
}
