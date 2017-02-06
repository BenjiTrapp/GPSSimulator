package gps.NMEA.sentences;

import gps.data.GPSData;
import gps.data.GPSDataEnumHolder.Status;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static gps.NMEA.sentences.NMEASentenceTypes.GPGSA;
import static gps.data.GPSData.*;
import static gps.data.GPSDataEnumHolder.*;

/**
 GSA - GPS DOP and active satellites. This sentence provides details on the nature of the fix. It includes the numbers
 of the satellites being used in the current solution and the DOP. DOP (dilution of precision) is an indication of the
 effect of satellite geometry on the accuracy of the fix. It is a unitless number where smaller is better. For 3D fixes
 using 4 satellites a 1.0 would be considered to be a perfect number, however for overdetermined solutions it is
 possible to see numbers below 1.0.

 There are differences in the way the PRN's are presented which can effect the ability of some programs to display this
 data. For example, in the example shown below there are 5 satellites in the solution and the null fields are scattered
 indicating that the almanac would show satellites in the null positions that are not being used as part of this
 solution. Other receivers might output all of the satellites used at the beginning of the sentence with the null
 field all stacked up at the end. This difference accounts for some satellite display programs not always being able
 to display the satellites being tracked. Some units may show all satellites that have ephemeris data without regard
 to their use as part of the solution but this is non-standard.

 $GPGSA,A,3,04,05,,09,12,,,24,,,,,2.5,1.3,2.1*39

 Where:
 GSA      Satellite status
 A        Auto selection of 2D or 3D fix (M = manual)
 3        3D fix - values include: 1 = no fix
 2 = 2D fix
 3 = 3D fix
 04,05... PRNs of satellites used for fix (space for 12)
 2.5      PDOP (dilution of precision)
 1.3      Horizontal dilution of precision (HDOP)
 2.1      Vertical dilution of precision (VDOP)
 *39      the checksum data, always begins with *
 *
 * @author Benjamin Trapp
 *
 */
@annotations.NMEASentence(GPGSA)
public class GSASentence implements NMEASentence
{
    private static final String DELIMITER = ",";
    private static final int MAX_SATELLITES = 12;
    private static final int ABORT_CNT = 50;

    @Override
	public synchronized String getSentence()
	{
	    String result;

		if (getStatus() == Status.A)
		{
		    return new NMEASentenceBuilder(GPGSA).append(SatelliteMode.A.toString())
                                                 .append(getFixTypeAsInteger())
                                                 .append(createSatellitePRNs())
                                                 .append(getPDOP())
                                                 .append(getHDOP())
                                                 .append(getVDOP())
                                                 .appendChecksum()
                                                 .build();
		} else{
			result = GPGSA.getSentenceType()
                    + ",,,,,,,,,,,,,,,,,*6E";
        }
        return result;
	}

	private synchronized int getFixTypeAsInteger(){
	    switch (GPSData.getFixType()){
            case GPS_FIX_UNKNOWN: return 1;
            case GPS_FIX_2D: return 2;
            case GPS_FIX_3D: return 3;
            default: throw new RuntimeException("Try to operate with an unknown GPS FixType");
        }
    }

    String createSatellitePRNs(){
	    int currentSatellites = Integer.parseInt(GPSData.getSatellites());
        Random rnd = new Random();
        List<Integer> processedSatellites = new ArrayList<>();
        String[] s = new String[MAX_SATELLITES];
        String result = "";
        int i = 0;

        for(int x = 0; x < MAX_SATELLITES; x++){s[x] = DELIMITER;}

        do{
            int tmpValue = rnd.nextInt(MAX_SATELLITES);

            if(tmpValue <= currentSatellites && !processedSatellites.contains(tmpValue)){
                String tmpString = (tmpValue < 10)? "0" + tmpValue + DELIMITER: Integer.toString(tmpValue) + DELIMITER;
                s[tmpValue] = tmpString;
                processedSatellites.add(tmpValue);
                i++;
            }
        } while (i != currentSatellites && i < ABORT_CNT);

        for(int x = 0; x < MAX_SATELLITES; x++){result += s[x];}

        return result;
    }
}
