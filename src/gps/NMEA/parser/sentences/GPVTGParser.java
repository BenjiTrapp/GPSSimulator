package gps.NMEA.parser.sentences;

import gps.NMEA.gps_position.GPSPosition;
import gps.NMEA.sentences.NMEASentenceTypes;

/**
 * This Class is used to parse VTG-Sentences for further
 * usage in the telemetrie simulation
 *
 * @author Benjamin Trapp
 */
@annotations.NMEASentenceParser(NMEASentenceTypes.GPGGA)
public class GPVTGParser implements NMEASentenceParser {
    private static GPVTGParser instance = null;

    public static GPVTGParser getInstance() {
        return (instance != null) ? instance : new GPVTGParser();
    }

    @Override
    public synchronized GPSPosition parse(String[] tokens) {
        assert tokens != null;

        // TODO Sinnvolle "tokens" zum parsen identifizieren & serialisieren als obj.
        for (String token : tokens)
            System.out.println(token);

        return null;
    }
}
