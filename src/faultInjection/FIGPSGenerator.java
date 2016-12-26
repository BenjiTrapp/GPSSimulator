package faultInjection;

import java.net.Socket;
import java.util.Random;
import java.util.Timer;

import gps.generator.DataGenTask;
import faultInjection.communication.FIStringWriter;
import faultInjection.pertubation.EPertubationModes;
import faultInjection.tasks.FIGGATask;
import faultInjection.tasks.FIRMCTask;

/**
 * This Class is used to combine the internal Components of the GPS-Generator
 * and constructs an instance to work with. This Class is redefined and assumed
 * to separate the functionality from the virtual prototype
 * 
 * @author Benjamin Trapp
 */
public class FIGPSGenerator
{
	private Timer timer = new Timer();
	private FIStringWriter fiWriter = null ;
	private Random rnd = new Random();
	private final static int ONE_MHz = 1000;
	private final static int DELAY_FOR_RMC = 1000;
	private final static int MIN_RMC_GEN_TIME = 500;
	private final static int MAX_RMC_GEN_TIME = 2100;
	
	/**
	 * Creates a GPS-Generator with the passed DataGenTask Instance
	 * @param task Instance to the DataGenTask 
	 * @param period Period for in which the DataGenTask shall modify the Data
	 */
	public FIGPSGenerator(DataGenTask task, int period, Socket socket, EPertubationModes mode)
	{
		initCommunication(socket, mode);
		timer.scheduleAtFixedRate(task, 0, period);
	}
	
	/**
	 * Creates a GPS-Generator with the passed DataGenTask Instance
	 * @param task Instance to the DataGenTask 
	 * @param period Period for in which the DataGenTask shall modify the Data
	 */
	public FIGPSGenerator(DataGenTask task, int period, FIStringWriter fiWriter)
	{
		this.fiWriter = fiWriter;
		timer.scheduleAtFixedRate(task, 0, period);
	}
	
	/**
	 * Creates a GPS-Generator with the passed DataGenTask Instance
	 * @param task Instance to the DataGenTask 
	 * @param period Period for in which the DataGenTask shall modify the Data
	 */
	public FIGPSGenerator(DataGenTask task, int period, EPertubationModes mode)
	{
		fiWriter = FIStringWriter.getInstance(mode);
		timer.scheduleAtFixedRate(task, 0, period);
	}
	
	/**
	 * Generates GPRMC Data within a random interval of 0,5 to 2,6 seconds
	 * 
	 */
	public void generateFIRMCData()
	{
			int tmp = rnd.nextInt(MAX_RMC_GEN_TIME) + MIN_RMC_GEN_TIME;
			timer.schedule(new FIRMCTask(fiWriter), DELAY_FOR_RMC, tmp);
	}

	/**
	 * Generates GPGGA Data with a fix rate of 1Mhz 
	 * 
	 * For more Info take a look at:
	 * http://www.proz.com/kudoz/english_to_german/electronics_elect_eng/3813817-input_gps_message_rate_for_the_gga_message.html
	 */
	public void generateFIGGAData()
	{
		timer.scheduleAtFixedRate(new FIGGATask(fiWriter), 0, ONE_MHz);
	}

	/**
	 * Initializes the Communication with the passed Socket
	 * @param socket Socket to establish the communication (creates a StringWriter)
	 */
	private void initCommunication(Socket socket, EPertubationModes mode)
	{
		if(socket != null)
			FIStringWriter.initInstance(socket, mode);
		
		fiWriter = FIStringWriter.getInstance(mode);
	}
	
	/**
	 * closes the Communication (StreamWriter)
	 */
	public void closeCommunication()
	{
		fiWriter.closeOutStream();
	}
	
	/**
	 * Function to stop the timer task
	 */
	public void stopTimerTask()
	{
		timer.cancel();
	}
	
	/**
	 * Destroys the FIGPSGenerator 
	 */
	public void destroy()
	{
		closeCommunication();
		stopTimerTask();
	}
}
