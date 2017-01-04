package faultInjection.pertubation.perturbation_functions.perturbation_strategies;

import faultInjection.pertubation.perturbation_functions.modes.PerturbationModes;
import gps.data.GPSData;
import gps.data.GPSDataEnumHolder;

import java.util.concurrent.atomic.AtomicBoolean;

public class StuckAtStrategy extends AbstractPerturbationStrategy {

    private static volatile AtomicBoolean isRunning = new AtomicBoolean(false);

    public StuckAtStrategy() {super(PerturbationModes.STUCK_AT);}

    public void setStuckedTime(int millis){
        if (millis <= 0) throw new AssertionError();

        try {
            Thread.sleep(millis);
        } catch (InterruptedException ignored) {
        }finally {this.turnOff();}
    }

    private void turnOff(){
        isRunning.set(false);}

    @Override
    public void perturb(String line2perturb) {
        if (!isRunning.get()) {
            isRunning.set(true);
            System.err.println("Start jamming the GPS Position");
            do{GPSData.reinitialize();} while (!isRunning.get());
        }
    }
}
