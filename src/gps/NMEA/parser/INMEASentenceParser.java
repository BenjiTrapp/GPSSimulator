package gps.NMEA.parser;

/**
 * Interface for the gps.NMEA-Parser implementations
 * 
 * @author Benjamin Trapp
 *
 */
public interface INMEASentenceParser
{
	/**
	 * USe this function to parse the passed tokens 
	 * @param tokens Tokens from the splitted gps.NMEA-Sentence
	 * @return GPSPosition that contains the complete parsed
	 * informations
	 */
	public GPSPosition parse(String[] tokens);
}
