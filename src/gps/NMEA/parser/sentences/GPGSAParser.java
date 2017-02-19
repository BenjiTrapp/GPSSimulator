package gps.NMEA.parser.sentences;

import gps.NMEA.gps_position.GPSPosition;
import gps.NMEA.gps_position.GPSPositionBuilder;
import gps.NMEA.sentences.NMEASentenceTypes;

/**
 * This Class is used to parse GSA-Sentences for further
 * usage in the telemetrie simulation
 *
 * @author Benjamin Trapp
 */
@annotations.NMEASentenceParser(NMEASentenceTypes.GPGSA)
public class GPGSAParser implements NMEASentenceParser {
    private static GPGSAParser instance = null;

    public static GPGSAParser getInstance() {
        return (instance != null) ? instance : new GPGSAParser();
    }

    @Override
    public synchronized GPSPosition parse(String... tokens) {
        assert tokens != null;

        // TODO Sinnvolle "tokens" zum parsen identifizieren & serialisieren als obj.
        for (String token : tokens)
            System.out.println(token);

        return null;
    }
}
