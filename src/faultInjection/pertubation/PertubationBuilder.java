package faultInjection.pertubation;

import java.util.Set;
import java.util.TreeSet;

public final class PertubationBuilder {
    private final Set<EPertubationModes> pertubationModes = new TreeSet<>();
    private final PerturbationFactory perturbationFactory = new PerturbationFactory();

    public void build(){
        pertubationModes.forEach(perturbationFactory::build);
    }

    public PertubationBuilder addPertubationMode(EPertubationModes pertubationMode){
        this.pertubationModes.add(pertubationMode);
        return this;
    }
}