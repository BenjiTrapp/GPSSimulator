/**
 *
 */
package faultInjection.communication_jammer;

import faultInjection.pertubation.EPertubationModes;
import faultInjection.pertubation.PertubationFunctions;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Random;

/**
 * This Class is redefined and assumed
 * to separate the functionality from the virtual prototype.
 * <p>
 * The ComJammer is used to inject the fault into
 * the SUT (gps.NMEA.NMEAParser)
 *
 * @author Benjamin Trapp
 */
public final class ComJammer {
    private Socket socket = null;
    private static ComJammer instance = null;
    private Random rnd = null;
    private static final int FAULT_PERCENTAGE = 5;
    private static final String NEW_LINE = "\n";
    private EPertubationModes mode = EPertubationModes.RANDOM_ASCII;
    private String stuckAtVal = null;
    private static volatile boolean isStucked = false;
    private volatile boolean hasDashed = false;
    private CountDown cd = null;
    private OutputStream outStream = null;

    private ComJammer(EPertubationModes mode) {

        this.mode = mode;
        this.rnd = new Random();
        this.cd = new CountDown();
    }

    private ComJammer(Socket socket, EPertubationModes mode) {
        try {
            outStream = socket.getOutputStream();
        } catch (IOException e) {
            System.err.println("ERROR @ in- /output Stream, IOException");
        }
        this.mode = mode;
        rnd = new Random();
        cd = new CountDown();
    }

    /**
     * Gets the Instance of the StringReader back. If the instance
     * was initialized properly the passed socket will be used otherwise
     * a default instance is created with the ServerSocket 4711.
     *
     * Note: After the instance is set up once the instance can't
     * be changed or modified! So if you want to use a specific ServerSocket
     * you have to initialize the instance first via initInstance(ServerSocket)
     *
     * @return The one and only Instance of the StringReader
     */
    public static ComJammer getInstance(EPertubationModes mode) throws IOException {
        assert  mode != null;
        return instance == null ? new ComJammer(new Socket("localhost",6711), mode) : instance;
    }

    public static ComJammer getInstance(Socket socket, EPertubationModes mode) {
        assert mode != null;
        assert socket != null;

        return instance == null ? new ComJammer(socket, mode) : instance;
    }

    public synchronized void send(String msg) {
        assert msg != null;

        //Save the value for the stuck-at bug
        if (!isStucked)
            stuckAtVal = msg;

        //Perturb the message
        msg = evalMode(mode, msg);

        //Append "new line"
        msg += NEW_LINE;

        try {
            outStream.write(msg.getBytes());
            outStream.flush();
        } catch (IOException e) {
            System.err.println("ERROR @ write output stream");
        } finally {
            try {
                outStream.close();
            } catch (IOException ignored) {
            }
        }
    }

    /**
		 * Function to evaluate the wanted perturbation mode and realize
		 * the perturbation function in this way
		 * @param mode Mode of the Perturbation. Use STUCK_AT for creating a
		 * stuck at bug, DASH for make a random dash in the GPS-Coordinates and
		 * RANDOM_ASCII to simulate a perturbation of the RS232 Interface
		 * @param msg The message in which the fault shall be injected
		 * @return A faulty message based on the chosen perturbation mode
		 */
		private  String evalMode(EPertubationModes mode, String msg) {
            String faultyMsg = msg;
            int tmp;

            switch (mode) {
                case STUCK_AT:  	//Check if the function is stucked
                                    if(!isStucked) {
                                        isStucked = true;
                                        cd.stuckAtCountDown(rnd.nextInt(15),rnd.nextInt(20));
                                    }else {faultyMsg = stuckAtVal;}
                                    break;

                case DASH:  		//Avoid that this function is called to often
                                    if(!hasDashed) {
                                        hasDashed = true;
                                        cd.dashCountDown(rnd.nextInt(25));
                                    }
                                    break;

                case RANDOM_ASCII:	// Calculate random percentage if the perturbation is applied or not
                                    tmp = rnd.nextInt(500 * FAULT_PERCENTAGE) / 100;
                                    if (tmp <= FAULT_PERCENTAGE && tmp >= 0)
                                        faultyMsg = PertubationFunctions.randomASCIIChar(msg);
                                    break;
                default:            break;
            }

            return faultyMsg;
	}

	/**
	 * Callback Function used by the CountDown timer to signal
	 * that the stuck-at bug is gone
	 */
	synchronized static void stuckAt()
	{
		System.err.println("STUCK");
		isStucked = false;
	}
}