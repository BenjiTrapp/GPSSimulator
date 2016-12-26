package faultInjection.pertubation;

import java.util.Set;
import java.util.TreeSet;

public final class PertubationBuilder {
    private final Set<EPertubationModes> pertubationModes = new TreeSet<>();
    private final PertubationFactory pertubationFactory = new PertubationFactory();

    public void build(){
        pertubationModes.forEach(pertubationFactory::build);
    }

    public PertubationBuilder addPertubationMode(EPertubationModes pertubationMode){
        this.pertubationModes.add(pertubationMode);
        return this;
    }
}