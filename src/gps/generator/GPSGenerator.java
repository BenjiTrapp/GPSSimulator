package gps.generator;

import java.net.Socket;
import java.util.Random;
import java.util.Timer;

import communication.StringWriter;
import gps.generator.datagen_tasks.DataGenTask;
import gps.generator.sentence_tasks.GGATask;
import gps.generator.sentence_tasks.GSATask;
import gps.generator.sentence_tasks.RMCTask;

/**
 * This Class is used to combine the internal Components of the GPS-Generator
 * and constructs an instance to work with
 *
 * @author Benjamin Trapp
 */
public class GPSGenerator {
    private Timer timer = new Timer();
    private StringWriter strWriter = null;
    private Random rnd = new Random();
    private static final int ZERO_DELAY = 0;
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
    public GPSGenerator(DataGenTask task, int period, Socket socket) {
        this.strWriter = initCommunication(socket);
        this.timer.scheduleAtFixedRate(task, ZERO_DELAY, period);
    }

    /**
     * Creates a GPS-Generator with the passed DataGenTask Instance
     *
     * @param task   Instance to the DataGenTask
     * @param period Period for in which the DataGenTask shall modify the Data
     */
    public GPSGenerator(DataGenTask task, int period, StringWriter strWriter) {
        this.strWriter = strWriter;
        this.timer.scheduleAtFixedRate(task, ZERO_DELAY, period);
    }

    /**
     * Creates a GPS-Generator with the passed DataGenTask Instance
     *
     * @param task   Instance to the DataGenTask
     * @param period Period for in which the DataGenTask shall modify the Data
     */
    public GPSGenerator(DataGenTask task, int period) {
        this.strWriter = StringWriter.getInstance();
        this.timer.scheduleAtFixedRate(task, ZERO_DELAY, period);
    }

    /**
     * Generates GPRMC Data within a random interval of 0,5 to 2,6 seconds
     */
    public void generateRMCData() {
        int tmp = rnd.nextInt(MAX_RMC_GEN_TIME) + MIN_RMC_GEN_TIME;
        this.timer.schedule(new RMCTask(strWriter), DELAY_FOR_RMC, tmp);
    }

    /**
     * Generates GPGGA Data with a fix rate of 1Mhz
     * <p>
     * For more Info take a look at:
     * http://www.proz.com/kudoz/english_to_german/electronics_elect_eng/3813817-input_gps_message_rate_for_the_gga_message.html
     */
    public void generateGGAData() {
        this.timer.scheduleAtFixedRate(new GGATask(strWriter), ZERO_DELAY, ONE_MHz);
    }

    /**
     * Generates GPGSA Data with a fix rate of 1Mhz
     */
    public void generateGSAData() {this.timer.scheduleAtFixedRate(new GSATask(strWriter), ZERO_DELAY, ONE_MHz);}

    /**
     * Initializes the Communication with the passed Socket
     *
     * @param socket Socket to establish the communication (creates a StringWriter)
     */
    private StringWriter initCommunication(Socket socket) {
        assert (socket != null);
        StringWriter.initInstance(socket);
        return StringWriter.getInstance();
    }
}
