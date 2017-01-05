package faultInjection.pertubation.perturbation_functions.perturbation_strategies;

import faultInjection.pertubation.perturbation_functions.modes.PerturbationModes;
import gps.data.GPSData;
import gps.data.GPSDataEnumHolder;

import java.util.concurrent.atomic.AtomicBoolean;

public class StuckAtStrategy extends AbstractPerturbationStrategy {

    private static volatile boolean isRunning = false;

    public StuckAtStrategy() {super(PerturbationModes.STUCK_AT);}

    public void setStuckedTime(int millis){
        if (millis <= 0) throw new AssertionError();

        try {
            Thread.sleep(millis);
        } catch (InterruptedException ignored) {
        }finally {this.turnOff();}
    }

    private synchronized void turnOff(){isRunning = true;}

    @Override
    public void perturb(String line2perturb) {
            System.err.println("Start jamming the GPS Position");
            new Thread(() -> {
                while (isRunning){GPSData.stuckAt();
                try {Thread.sleep(50);} catch (InterruptedException ignored) {}
            }}).start();
    }
}
