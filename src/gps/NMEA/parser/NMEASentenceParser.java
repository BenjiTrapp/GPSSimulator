package gps.NMEA.parser;

import gps.NMEA.gps_position.GPSPosition;

/**
 * Interface for the gps.NMEA-Parser implementations
 * 
 * @author Benjamin Trapp
 *
 */
public interface NMEASentenceParser
{
	/**
	 * USe this function to parse the passed tokens 
	 * @param tokens Tokens from the splitted gps.NMEA-Sentence
	 * @return GPSPosition that contains the complete parsed
	 * informations
	 */
	GPSPosition parse(String[] tokens);
}
