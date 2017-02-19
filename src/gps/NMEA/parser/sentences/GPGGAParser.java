package gps.NMEA.parser.sentences;

import gps.NMEA.gps_position.GPSPosition;
import gps.NMEA.gps_position.GPSPositionBuilder;
import gps.NMEA.sentences.NMEASentenceTypes;

/**
 * This Class is used to parse GGA-Sentences for further
 * usage in the telemetrie simulation
 *
 * @author Benjamin Trapp
 */
@annotations.NMEASentenceParser(NMEASentenceTypes.GPGGA)
public class GPGGAParser implements NMEASentenceParser {
    private static final int MAX_AMOUNT_OF_NEEDED_TOKENS = 10;
    private static GPGGAParser instance = null;

    public static GPGGAParser getInstance() {
        return (instance != null) ? instance : new GPGGAParser();
    }

    @Override
    public synchronized GPSPosition parse(String... tokens) {
        assert tokens != null;
        assert tokens.length >= MAX_AMOUNT_OF_NEEDED_TOKENS;

        return new GPSPositionBuilder()
                .addTime(Double.valueOf(tokens[1]))
                .addLatitude(Double.valueOf(tokens[2]))
                .addLongitude(Double.valueOf(tokens[4]))
                .addQuality(Double.valueOf(tokens[6]))
                .addAltitude(Double.valueOf(tokens[9]))
                .build();
    }
}
