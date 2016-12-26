/**
 * 
 */
package faultInjection.communication;

import faultInjection.pertubation.CountDown;
import faultInjection.pertubation.EPertubationModes;
import faultInjection.pertubation.PertubationFunctions;

import java.io.IOException;
import java.net.Socket;
import java.util.Random;

/**
 * This Class is redefined and assumed
 * to separate the functionality from the virtual prototype.
 * 
 * The FIStringWriter is used to inject the fault into
 * the SUT (gps.NMEA.NMEAParser)
 * 
 * @author Benjamin Trapp
 *
 */
public final class FIStringWriter extends AFICom
{
		private static Socket socket = null;
		private static FIStringWriter instance = null;
		private static Random rnd = null;
		private static int faultPercentage = 5;
		private static EPertubationModes mode = EPertubationModes.RANDOM_ASCII;
		private static String stuckAtVal = null;
		private static volatile boolean isStucked = false;
		private static volatile boolean hasDashed = false;
		private static CountDown cd = null;
		
		/**
		 * Creates a new Communication Object
		 * 
		 * @param socket
		 *            Socket for the communication
		 */
		private FIStringWriter(Socket socket, EPertubationModes mode)
		{
			super(socket);
			FIStringWriter.mode = mode;
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
		public static FIStringWriter getInstance(EPertubationModes mode)
		{
			try
			{
				if (instance == null && socket == null)
					instance = new FIStringWriter(new Socket("localhost",6711),mode);

				if (instance == null && socket != null)
					instance = new FIStringWriter(socket,mode);

			} catch (IOException e)
			{
				instance.closeOutStream();
				e.printStackTrace();
			}

			return instance;
		}

		/**
		 * Initializes the StringReader Instance if necessary
		 * Caution: This can only be done once and before calling
		 * the getInstance() Method!
		 * @param socket Socket that the StringReader shall use
		 * @return true if the initialization was done else false
		 */
		public static boolean initInstance(Socket socket, EPertubationModes mode)
		{
			if(FIStringWriter.socket != null)
			{
				FIStringWriter.socket = socket;
				FIStringWriter.mode = mode;
				return true;
			}
			
			return false;
		}
		
		@Override
		public synchronized void send(String msg)
		{
			if (msg == null)
				throw new NullPointerException("passed String is null");
			
			//Save the value for the stuck-at bug
			if(!isStucked)
				stuckAtVal = msg;
			
		    //Perturb the message 
			msg = evalMode(mode, msg);
				
			//Append "new line"
			msg += "\n";
			
			try
			{
				super.outStream.write(msg.getBytes());
				super.outStream.flush();
			} catch (IOException e)
			{
				System.err.println("ERROR @ write output stream");
				instance.closeOutStream();
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
		private synchronized String evalMode(EPertubationModes mode, String msg)
		{
		String faultyMsg = msg;
		int tmp = -1;

		switch (mode)
		{
		case STUCK_AT:  	//Check if the function is stucked
							if(!isStucked)
							{
								isStucked = true;
								cd.stuckAtCountDown(rnd.nextInt(15),rnd.nextInt(20));
							}else
							{
								faultyMsg = stuckAtVal;
							}
							break;

		case DASH:  		//Avoid that this function is called to often
							if(!hasDashed)
							{
								hasDashed = true;
								cd.dashCountDown(rnd.nextInt(25));
							}
							break;

		case RANDOM_ASCII:	// Calculate random percentage if the perturbation is applied or not
							tmp = rnd.nextInt(500 * faultPercentage) / 100;
							if (tmp <= faultPercentage && tmp >= 0)
								faultyMsg = PertubationFunctions.randomASCIIChar(msg);
							break;

		default:		
							break;
		}
		
		return faultyMsg;
	}
	
	/**
	 * Callback Function used by the CountDown timer to signal
	 * that the stuck-at bug is gone
	 */	
	public synchronized static final void stuckAt()
	{
		System.err.println("STUCK");
		isStucked = false;
	}
}