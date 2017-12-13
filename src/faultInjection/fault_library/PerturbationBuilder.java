package faultInjection.fault_library;

import com.google.common.collect.ImmutableMap;
import faultInjection.fault_library.down_counter.CallbackCountDownBuilder;
import faultInjection.fault_library.perturbation_strategies.AbstractPerturbationStrategy;

import java.util.*;

import static faultInjection.fault_library.PerturbationBuilder.PerturbationModes.*;

public class PerturbationBuilder {
    public enum PerturbationModes {COUNT,PERIOD,DELAY}
    private List<AbstractPerturbationStrategy> strategies = new ArrayList<>();
    private Random random = new Random();
    private boolean useRandomness;
    private int count = 5;
    private int period = 1000;
    private int delay = 0;

    public PerturbationBuilder withPresetCountInSeconds(int count){
        assert  count >= 0;

        this.count = count;
        return this;
    }

    public PerturbationBuilder withTimerPeriod(int period){
        assert  period >= 0;

        this.period = period;
        return this;
    }

    public PerturbationBuilder withTimerDelay(int delay){
        assert  delay >= 0;

        this.delay = delay;
        return  this;
    }

    public PerturbationBuilder addStrategy(AbstractPerturbationStrategy strategy){
        assert strategy != null;

        this.strategies.add(strategy);
        return this;
    }

    public PerturbationBuilder useRandomnessForConfiguration(){
        useRandomness = true;
        return this;
    }

    public void build(){
        if(useRandomness) {
            this.count += random.nextInt(4);
            this.period += random.nextInt(1000);
            this.delay += random.nextInt(3000);
        }

        strategies.forEach(strategy -> new CallbackCountDownBuilder().registerEvent(strategy)
                                                                     .presetCountInSeconds(count)
                                                                     .setTimerDelay(delay)
                                                                     .setTimerPeriod(period)
                                                                     .startCountDown());
    }

    public ImmutableMap<PerturbationModes, Integer> getConfiguration() {
        return ImmutableMap.<PerturbationModes,Integer>builder().put(COUNT, count)
                                                                .put(PERIOD, period)
                                                                .put(DELAY, delay)
                                                                .build();
    }
}
