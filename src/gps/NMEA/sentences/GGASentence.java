package gps.NMEA.sentences;

import gps.data.GPSData;
import gps.data.GPSDataEnumHolder.Status;
/**
 * Uses the Data that is deposited in the gps.data.GPSData Class to generate a
 * gps.NMEA GPSGGA Sentence. Look below for further info and the data types.
 * The Info is adopted from http://www.kowoma.de/gps/zusatzerklaerungen/NMEA.htm
 * 
 * $GPGGA,191410,4735.5634,N,00739.3538,E,1,04,4.4,351.5,M,48.0,M,,*45
 *     ^      ^           ^            ^ ^  ^   ^       ^     
 *     |      |           |            | |  |   |       |    
 *     |      |           |            | |  |   |       Height Geoid minus 
 *     |      |           |            | |  |   |       Height Ellipsoid (WGS84)
 *     |      |           |            | |  |   |       in meters (48.0,M)
 *     |      |           |            | |  |   |
 *     |      |           |            | |  |   Height below sea level (via Geoid)
       |      |           |            | |  |   in meters (351.5,M)
       |      |           |            | |  |
       |      |           |            | |  HDOP (horizontal dilution
       |      |           |            | |  of precision) 
       |      |           |            | |
       |      |           |            | Amount of Satellites 
       |      |           |            | 
       |      |           |            Quality of the measurement
       |      |           |            (0 = invalid)
       |      |           |            (1 = GPS)
       |      |           |            (2 = DGPS)
       |      |           |            (6 = estimated only @gps.NMEA-0183 2.3)
       |      |           | 
       |      |           Longitude
       |      |
       |      Latitude 
       |
       Time
 * @author Benjamin Trapp
 *
 */
public class GGASentence extends ANMEASentence
{
	@Override
	public String getName()
	{
		return "GPGGA";
	}
	
	@Override
	public String getSentence()
	{
		String sentence;
		String ns = GPSData.getNS().name().substring(0, 1);
		String ew = GPSData.getEW().name().substring(0, 1);

		if (GPSData.getStatus() == Status.A)
		{
			StringBuffer buf = new StringBuffer("$" + getName());
			append(buf, getTimestamp()); 			
			append(buf, ANMEASentence.getNMEALatitude());		
			append(buf, ns);						
			append(buf, ANMEASentence.getNMEALongitude());	
			append(buf, ew);						
			append(buf, GPSData.getQuality());	 	
			append(buf, GPSData.getSatellites());	
			append(buf, GPSData.getHDOP());			
			append(buf, GPSData.getAltitude());		
			append(buf, "M");						
			append(buf, "0");						
			append(buf, "M");						
			append(buf, "");					
			append(buf, "");				
			appendCheckSum(buf);
			sentence = buf.toString();
		} else
			sentence = "GPGGA," + getTimestamp() + ",,,,,,,,,,,,,*7A";

		return sentence;
	}
}
