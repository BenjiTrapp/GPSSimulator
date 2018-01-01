package faultInjection.generator;

import java.util.Random;
import java.util.Timer;

import communication.communication_jammer.ComJammer;
import gps.NMEA.sentences.GGASentence;
import gps.NMEA.sentences.RMCSentence;
import gps.generator.datagen_tasks.DataGenTask;

/**
 * This Class is used to combine the internal Components of the GPS-Generator
 * and constructs an instance to work with. This Class is redefined and assumed
 * to separate the functionality from the virtual prototype
 *
 * @author Benjamin Trapp
 */
public class PerturbedGPSGenerator {
    private Timer timer;
    private GGASentence ggaSentence = new GGASentence();
    private RMCSentence rmcSentence = new RMCSentence();
    private final static int MIN_RMC_GEN_TIME = 500;
    private final static int MAX_RMC_GEN_TIME = 2100;

    /**
     * Creates a GPS-Generator with the passed DataGenTask Instance
     * @param task   Instance to the DataGenTask
     * @param period Period for in which the DataGenTask shall modify the Data
     */
    public PerturbedGPSGenerator(DataGenTask task, int period, ComJammer jammerInstance) {
        Random rnd = new Random();
        timer = new Timer();
        ComJammer jammerInstance1 = jammerInstance;
        timer.scheduleAtFixedRate(task, 0, period);
    }

    /**
     * Generates GPRMC Data within a random interval of 0,5 to 2,6 seconds
     */
    void generateFIRMCData() {
        //timer.schedule(new ComJammerByteManipulationTask(, null));
    }

    /**
     * Generates GPGGA Data with a fix rate of 1Mhz
     * For more Info take a look at:
     * http://www.proz.com/kudoz/english_to_german/electronics_elect_eng/3813817-input_gps_message_rate_for_the_gga_message.html
     */
    void generateFIGGAData() {
//        timer.scheduleAtFixedRate(new ComJammerByteManipulationTask(fiWriter), 0, ONE_MHz);
    }

    /**
     * Function to stop the timer task
     */
    public void stopTimerTask() {
        timer.cancel();
    }
}
