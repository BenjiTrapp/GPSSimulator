package gps.NMEA.sentences;

import gps.NMEA.utils.ChecksumUtilities;
import gps.data.GPSDataEnumHolder.Status;

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
public class RMCSentenceNEW implements NMEASentence {

    private static final String DELIMITER = ",";

	@Override
	public synchronized String getSentence() {
        String result;

		if (getStatus() == Status.A) {
		    String tmp = new NMEASentenceBuilder(NMEASentenceTypes.GPRMC)
					.append(getVelocity())
					.append(Integer.toString(getCourse()))
                    .append(getDatetime())
					.append(",")
                    .appendChecksum(getMode().name().substring(0, 1))
					.build();
		    result = tmp + ChecksumUtilities.getCRC(tmp);
            System.err.println("VALID? " + ChecksumUtilities.isChecksumValid(result));
        } else {
			result = String.valueOf(NMEASentenceTypes.GPRMC)
                                    + DELIMITER + getTimestamp()
                                    + ",V,,,,,,,,,,N*31";
		}

        return result;
	}
}
