package starter;


import gps.ParserFactory;
/**
 * This class is used to create and run a GPSParser that
 * parses RMC and GGA Sentences Based on gps.NMEA 0183
 * 
 * Attention: Make sure that this Class is running before the 
 * gps.data.GPSGenerator or FIGPSGenerator is started, otherwise
 * this class will end in an Exception because the ServerSocket
 * is missing!
 */
public class GPSParserStarter
{

	/**
	 * The Main to run the GPS-Generator
	 * @param args not used
	 */
	public static void main(String[] args)
	{
		new ParserFactory().build();
	}
}
