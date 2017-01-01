package gps.NMEA.sentences;

import gps.NMEA.utils.ChecksumUtilities;
import gps.data.GPSData;
import gps.data.GPSDataEnumHolder.Status;

import static gps.NMEA.sentences.NMEASentenceTypes.*;
import static gps.data.GPSData.*;

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
       |      | |           007" 39.3538' East
       |      | |                        
       |      | Latitude with Direction (N=North, S=South)
       |      | 46" 35.5634' North
       |      |
       |      Status of determination: A=Active | V=void
       |
       Time of the determination: 19:14:10 (UTC-Time)
 * @author Benjamin Trapp
 */
public class RMCSentence implements NMEASentence {

    private static final String DELIMITER = ",";

	@Override
	public synchronized String getSentence() {
        String result;

		if (getStatus() == Status.A) {
		    result = new NMEASentenceBuilder(GPRMC)
					.append(getTimestamp())
					.append(getStatus().name())
					.append(getNMEALatitude())
					.append(getNS().name().substring(0, 1))
                    .append(getNMEALongitude())
					.append(getEW().name().substring(0, 1))
					.append(getVelocity())
					.append(getCourse())
                    .append(getDatetime())
					.appendNotDelimited(",")
                    .append(getMode().name().substring(0, 1))
                    .appendChecksum()
					.build();
        } else {
			result = String.valueOf(GPRMC)
                                    + DELIMITER + getTimestamp()
                                    + ",V,,,,,,,,,,N*31";
		}

        return result;
	}
}
