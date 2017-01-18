package gps.NMEA.parser.sentences;

import gps.NMEA.gps_position.GPSPosition;
import gps.NMEA.gps_position.GPSPositionBuilder;
import gps.NMEA.sentences.NMEASentenceTypes;

/**
 * This Class is used to parse RMC-Sentences for further
 * usage in the telemetrie simulation
 *
 * @author Benjamin Trapp
 */
@annotations.NMEASentenceParser(NMEASentenceTypes.GPRMC)
public class GPRMCParser implements NMEASentenceParser {
    private static final int MAX_AMOUNT_OF_NEEDED_TOKENS = 9;
    private static GPRMCParser instance = null;

    public static GPRMCParser getInstance() {
        return (instance != null) ? instance : new GPRMCParser();
    }

    @Override
    public synchronized GPSPosition parse(String[] tokens) {
        assert tokens != null;
        assert tokens.length >= MAX_AMOUNT_OF_NEEDED_TOKENS;

        return new GPSPositionBuilder()
                .addTime(Double.valueOf(tokens[1]))
                .addLatitude(Double.valueOf(tokens[3]))
                .addLongitude(Double.valueOf(tokens[5]))
                .addQuality(Double.valueOf(tokens[7]))
                .addAltitude(Double.valueOf(tokens[8]))
                .build();
    }
}
