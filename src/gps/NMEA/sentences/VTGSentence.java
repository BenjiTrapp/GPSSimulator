package gps.NMEA.sentences;

import static gps.NMEA.sentences.NMEASentenceTypes.GPVTG;
import static gps.data.GPSData.*;
import static gps.data.GPSDataEnumHolder.*;

/**
 * Uses the Data that is deposited in the gps.data.GPSData Class to generate a
 * gps.NMEA GPSRMC Sentence. Look below for further info and the data types.
 * The Info is adopted from http://www.kowoma.de/gps/zusatzerklaerungen/NMEA.htm
 *
 * $GGPVTG,0.7,T,0.8,M,13.0,N,24.0,K,D*2D
 *
 1	Track made good (degrees true)
 2	T: track made good is relative to true north
 3	Track made good (degrees magnetic)
 4	M: track made good is relative to magnetic north
 5	Speed, in knots
 6	N: speed is measured in knots
 7	Speed over ground in kilometers/hour (kph)
 8	K: speed over ground is measured in kph

 * https://www.trimble.com/OEM_ReceiverHelp/V4.44/en/NMEA-0183messages_VTG.html
 *
 * @author Benjamin Trapp
 */
@annotations.NMEASentence(GPVTG)
public class VTGSentence implements NMEASentence {

    private static final double TRACK_RELATIVE_TO_TRUE_NORTH = 0.014;
    private static final double TRACK_RELATIVE_TO_MAGNETIC_NORTH = 0.017;
    private static final double CONVERT_KNOTS_TO_KM_PER_HOUR = 1.852;

    @Override
    public synchronized String getSentence() {
        String result;

        if (getStatus() == Status.A) {
            result = new NMEASentenceBuilder(GPVTG)
                    .append( getCourse() * TRACK_RELATIVE_TO_TRUE_NORTH)
                    .append(Track.T.toString())
                    .append(getCourse() * TRACK_RELATIVE_TO_MAGNETIC_NORTH)
                    .append(Track.M.toString())
                    .append(getVelocity())
                    .append(Track.N.toString())
                    .append(Double.parseDouble(getVelocity()) * CONVERT_KNOTS_TO_KM_PER_HOUR)
                    .append(Track.K.toString())
                    .append(TrackModes.E.toString())
                    .appendChecksum()
                    .build();
        } else {
            result = String.valueOf(GPVTG)
                    + ",,,,,,,,,E*21";
        }

        return result;
    }
}
