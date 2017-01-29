package faultInjection.generator;

import java.io.IOException;
import java.net.Socket;
import java.util.Random;
import java.util.Timer;

import communication.communication_jammer.ComJammer;
import faultInjection.fault_library.byte_manipulation.BytePerturbationFunction;
import faultInjection.fault_library.modes.PerturbationModes;
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
    private ComJammer fiWriter = null;
    private Random rnd;
    private final static int ONE_MHz = 1000;
    private final static int DELAY_FOR_RMC = 1000;
    private final static int MIN_RMC_GEN_TIME = 500;
    private final static int MAX_RMC_GEN_TIME = 2100;

    /**
     * Creates a GPS-Generator with the passed DataGenTask Instance
     *
     * @param task   Instance to the DataGenTask
     * @param period Period for in which the DataGenTask shall modify the Data
     */
    PerturbedGPSGenerator(DataGenTask task, int period, Socket socket) {
        if(socket != null){ComJammer.initComJammer(socket);}
        fiWriter = ComJammer.getInstance();

        rnd = new Random();
        timer = new Timer();

        timer.scheduleAtFixedRate(task, 0, period);
    }

    /**
     * Creates a GPS-Generator with the passed DataGenTask Instance
     * @param task   Instance to the DataGenTask
     * @param period Period for in which the DataGenTask shall modify the Data
     */
    public PerturbedGPSGenerator(DataGenTask task, int period, ComJammer fiWriter) {
        rnd = new Random();
        timer = new Timer();
        this.fiWriter = fiWriter;
        timer.scheduleAtFixedRate(task, 0, period);
    }

    /**
     * Creates a GPS-Generator with the passed DataGenTask Instance
     * @param task   Instance to the DataGenTask
     * @param period Period for in which the DataGenTask shall modify the Data
     */
    public PerturbedGPSGenerator(DataGenTask task, int period, BytePerturbationFunction function) {
//        try {
//            fiWriter = ComJammer.getInstance(function);
//        } catch (IOException e) {
//            System.err.println("Failed to get the ComJammer Instance back. Error during Socket creation?!");
//        }
//        timer = new Timer();
//        timer.scheduleAtFixedRate(task, 0, period);
//        rnd = new Random();
    }

    /**
     * Generates GPRMC Data within a random interval of 0,5 to 2,6 seconds
     */
    void generateFIRMCData() {
        int tmp = rnd.nextInt(MAX_RMC_GEN_TIME) + MIN_RMC_GEN_TIME;
       // timer.schedule(new FIRMCTask(fiWriter), DELAY_FOR_RMC, tmp);
    }

    /**
     * Generates GPGGA Data with a fix rate of 1Mhz
     * For more Info take a look at:
     * http://www.proz.com/kudoz/english_to_german/electronics_elect_eng/3813817-input_gps_message_rate_for_the_gga_message.html
     */
    void generateFIGGAData() {
//        timer.scheduleAtFixedRate(new FIGGATask(fiWriter), 0, ONE_MHz);
    }

    /**
     * Function to stop the timer task
     */
    public void stopTimerTask() {
        timer.cancel();
    }
}
