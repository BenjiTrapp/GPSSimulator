package faultInjection.perturbation_functions.perturbation_strategies;

import faultInjection.perturbation_functions.down_counter.CallbackEvent;
import faultInjection.perturbation_functions.modes.PerturbationModes;

public abstract class AbstractPerturbationStrategy implements PerturbationStrategy, CallbackEvent {

    private PerturbationModes mode;

    AbstractPerturbationStrategy(PerturbationModes modes){
        this.mode = modes;
    }

    public PerturbationModes getMode(){
        return this.mode;
    }

    @Override
    public synchronized void notifyToPerturb() {
        System.err.println("Injecting strategy for PerturbationMode (" + mode.name() + ") behold ...");
        this.perturb();
    }

    @Override
    public abstract void perturb();

    public String toString(){
        return "Strategy for mode: " + getMode().name();
    }
}
