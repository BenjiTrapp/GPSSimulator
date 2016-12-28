package gps.generator;

import java.net.Socket;
import java.util.Random;
import java.util.Timer;

import communication.StringWriter;

/**
 * This Class is used to combine the internal Components of the GPS-Generator
 * and constructs an instance to work with
 * 
 * @author Benjamin Trapp
 */
public class GPSGenerator
{
	private Timer timer = new Timer();
	private StringWriter strWriter = null ;
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
	public GPSGenerator(DataGenTask task, int period, Socket socket)
	{
		initCommunication(socket);
		timer.scheduleAtFixedRate(task, 0, period);
	}
	
	/**
	 * Creates a GPS-Generator with the passed DataGenTask Instance
	 * @param task Instance to the DataGenTask 
	 * @param period Period for in which the DataGenTask shall modify the Data
	 */
	public GPSGenerator(DataGenTask task, int period, StringWriter strWriter)
	{
		this.strWriter = strWriter;
		timer.scheduleAtFixedRate(task, 0, period);
	}
	
	/**
	 * Creates a GPS-Generator with the passed DataGenTask Instance
	 * @param task Instance to the DataGenTask 
	 * @param period Period for in which the DataGenTask shall modify the Data
	 */
	public GPSGenerator(DataGenTask task, int period)
	{
		strWriter = StringWriter.getInstance();
		timer.scheduleAtFixedRate(task, 0, period);
	}
	
	/**
	 * Generates GPRMC Data within a random interval of 0,5 to 2,6 seconds
	 * 
	 */
	public void generateRMCData()
	{
			int tmp = rnd.nextInt(MAX_RMC_GEN_TIME) + MIN_RMC_GEN_TIME;
			timer.schedule(new RMCTask(strWriter), DELAY_FOR_RMC, tmp);		
	}

	/**
	 * Generates GPGGA Data with a fix rate of 1Mhz 
	 * 
	 * For more Info take a look at:
	 * http://www.proz.com/kudoz/english_to_german/electronics_elect_eng/3813817-input_gps_message_rate_for_the_gga_message.html
	 */
	public void generateGGAData()
	{
		timer.scheduleAtFixedRate(new GGATask(strWriter), 0, ONE_MHz);
	}

	/**
	 * Initializes the Communication with the passed Socket
	 * @param socket Socket to establish the communication (creates a StringWriter)
	 */
	private void initCommunication(Socket socket)
	{
		if(socket != null)
			StringWriter.initInstance(socket);
		
		strWriter = StringWriter.getInstance();
	}
}
