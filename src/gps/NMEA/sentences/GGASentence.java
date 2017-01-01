package gps.NMEA.sentences;

import gps.NMEA.utils.ChecksumUtilities;
import gps.data.GPSData;
import gps.data.GPSDataEnumHolder.Status;

import static gps.NMEA.sentences.NMEASentenceTypes.*;
import static gps.data.GPSData.*;

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
       |      |           |            (6 = estimated only @NMEA-0183 2.3)
       |      |           |
       |      |           Longitude
       |      |
       |      Latitude 
       |
       Time
 * @author Benjamin Trapp
 *
 */
public class GGASentence implements NMEASentence
{
    private static final String DELIMITER = ",";

	@Override
	public synchronized String getSentence()
	{

	    String result;

		if (getStatus() == Status.A)
		{
		    return new NMEASentenceBuilder(GPGGA)
                            .append(getTimestamp())
                            .append(getNMEALatitude())
                            .append(getNS().name().substring(0, 1))
                            .append(getNMEALongitude())
                            .append(getEW().name().substring(0, 1))
                            .append(getQuality())
                            .append(getSatellites())
                            .append(getHDOP())
                            .append(getAltitude())
                            .append("M")
                            .append("0")
                            .append("M")
                            .append(",")
							.appendChecksum()
                            .build();
		} else{
			result = GPGGA.name()
                    + DELIMITER
                    + getTimestamp()
                    + ",,,,,,,,,,,,,*7A";
        }
        return result;
	}
}
