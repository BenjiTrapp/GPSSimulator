package faultInjection.pertubation.perturbation_functions.perturbation_strategies;

import faultInjection.pertubation.perturbation_functions.modes.PerturbationModes;
import gps.data.GPSData;

public final class StuckErrorStrategy extends AbstractPerturbationStrategy {

    private volatile int millis = 1000;

    public StuckErrorStrategy() {super(PerturbationModes.STUCK_ERROR);}

    public synchronized void setStuckTime(int millis){
        assert millis <= 0;

        this.millis = millis;
    }

    private synchronized void timeToStayStuckAndTurnOff() {
        new Thread(() -> {
            try {
                Thread.sleep(millis);
            } catch (InterruptedException ignored) {
            } finally {this.turnOff();}
        }).start();
    }

    private synchronized void turnOff() {GPSData.stuckAtState(false);}

    @Override
    public synchronized void perturb() {
        new Thread(() -> GPSData.stuckAtState(true)).start();
        timeToStayStuckAndTurnOff();
    }
}
