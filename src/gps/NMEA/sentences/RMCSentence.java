package gps.NMEA.sentences;

import gps.data.GPSData;
import gps.data.GPSDataEnumHolder.Status;
/**
 * Uses the Data that is deposited in the gps.data.GPSData Class to generate a
 * gps.NMEA GPSRMC Sentence. Look below for further info and the data types.
 * The Info is adopted from http://www.kowoma.de/gps/zusatzerklaerungen/NMEA.htm
 * 
 * $GPRMC,191410,A,4735.5634,N,00739.3538,E,0.0,0.0,181102,0.4,E,A*19
       ^      ^ ^           ^            ^   ^   ^      ^     ^
       |      | |           |            |   |   |      |     |
       |      | |           |            |   |   |      |     New @gps.NMEA 2.3:
       |      | |           |            |   |   |      |     Kind of determination
       |      | |           |            |   |   |      |     A=autonomous (self)
       |      | |           |            |   |   |      |     D=differential
       |      | |           |            |   |   |      |     E=estimated (estimated)
       |      | |           |            |   |   |      |     N=not valid (invalid)
       |      | |           |            |   |   |      |     S=simulator
       |      | |           |            |   |   |      |   
       |      | |           |            |   |   |      Declination
       |      | |           |            |   |   |     
       |      | |           |            |   |   Date: 18.11.2002     
       |      | |           |            |   |        
       |      | |           |            |   Movement direction in degree
       |      | |           |            |
       |      | |           |            Speed over Ground
       |      | |           |            
       |      | |           Longitude with direction (E=East, W=West)
       |      | |           007� 39.3538' East
       |      | |                        
       |      | Latitude with Direction (N=North, S=South)
       |      | 46� 35.5634' North
       |      |
       |      Status of determination: A=Active | V=void
       |
       Time of the determination: 19:14:10 (UTC-Time)
 * @author O_o
 *
 */
public class RMCSentence extends ANMEASentence
{

	@Override
	public String getName()
	{
		return "GPRMC";
	}

	@Override
	public String getSentence()
	{
		String sentence;
		
		if (GPSData.getStatus() == Status.A)
		{
			String ns = GPSData.getNS().name().substring(0, 1);
			String ew = GPSData.getEW().name().substring(0, 1);
			String mode = GPSData.getMode().name().substring(0, 1);
			
			StringBuffer buf = new StringBuffer("$"+ getName());
			append(buf, getTimestamp());			
			append(buf, GPSData.getStatus().name());
			append(buf, ANMEASentence.getNMEALatitude());
			append(buf, ns);
			append(buf, ANMEASentence.getNMEALongitude());
			append(buf, ew);
			append(buf, GPSData.getVelocity());
			append(buf, GPSData.getCourse());
			append(buf, getDatestamp());
			append(buf, "");
			append(buf, "");
			appendCheckSum(buf, mode);
			sentence = buf.toString();
		} else
			sentence = ("GPRMC," + getTimestamp() + ",V,,,,,,,,,,N*31");

		return sentence;
	}

}
