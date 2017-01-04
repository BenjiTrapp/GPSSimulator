package faultInjection.pertubation.perturbation_functions.down_counter;

import java.util.Timer;
import java.util.TimerTask;

/**
 * This Class is used to make some time triggered
 * procedures during the fault-injection experiment
 *
 * @author Benjamin Trapp
 */
public class CallbackCountDownBuilder {
    private CallbackEvent event = null;
    private int count = -1;
    private int period = 1000;
    private int delay = 0;

    public CallbackCountDownBuilder registerEvent(CallbackEvent event){
        this.event = event;
        return this;
    }

    public CallbackCountDownBuilder presetCountInSeconds(int count){
        this.count = count;
        return this;
    }

    public CallbackCountDownBuilder setTimerPeriod(int period){
        this.period = period;
        return this;
    }

    public CallbackCountDownBuilder setTimerDelay(int delay){
        this.delay = delay;
        return  this;
    }


    public synchronized void startCountDown() {
        assert count > 0;
        assert event != null;

        final Timer timer = new Timer();
        TimerTask task = new TimerTask() {

            @Override
            public void run() {
                if (count > 0) {count--;}

                if (count <= 0) {
                    event.notifyToPerturb();
                    timer.cancel();
                }
            }
        };

        timer.schedule(task, delay, period);
    }
}
