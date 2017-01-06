package faultInjection.pertubation.perturbation_functions.perturbation_strategies;

import faultInjection.pertubation.perturbation_functions.modes.PerturbationModes;
import gps.data.GPSData;

public class StuckErrorStrategy extends AbstractPerturbationStrategy {

    private volatile int millis = 1000;

    public StuckErrorStrategy() {super(PerturbationModes.STUCK_ERROR);}

    public void setStuckTime(int millis){
        if (millis <= 0)
            throw new AssertionError();

        this.millis = millis;
    }

    private void spendSymbolicTime() {
        new Thread(() -> {
            try {
                Thread.sleep(millis);
            } catch (InterruptedException ignored) {
            } finally {this.turnOff();}
        }).start();
    }

    private synchronized void turnOff() {GPSData.stuckAtState(false);}

    @Override
    public void perturb() {
            new Thread(() -> GPSData.stuckAtState(true)).start();
            spendSymbolicTime();
    }
}
