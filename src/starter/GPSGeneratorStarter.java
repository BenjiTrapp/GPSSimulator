package starter;


import gps.GPSGeneratorFactory;
/**
 * This class is used to create and run a gps.data.GPSGenerator that
 * creates RMC and GGA Sentences Based on gps.NMEA 0183
 * 
 * Attention: Make sure that the Parser is running otherwise
 * this class will end in an Exception because the ServerSocket
 * is missing! 
 */
public class GPSGeneratorStarter
{
	/**
	 * The Main to run the GPS-Generator
	 * @param args not used
	 */
	public static void main(String[] args)
	{
		GPSGeneratorFactory gen = new GPSGeneratorFactory();
		gen.build();
	}
}
