package gps.NMEA.parser;

import gps.NMEA.gps_position.GPSPosition;
import gps.NMEA.gps_position.GPSPositionBuilder;

/**
 * This Class is used to parse GGA-Sentences for further
 * usage in the telemetrie simulation
 * @author Benjamin Trapp
 *
 */
class GPGGAParser implements NMEASentenceParser
{
    private static GPGGAParser instance = null;

    public static GPGGAParser getInstance(){
        return (instance != null) ? instance : new GPGGAParser();
    }

	@Override
    public synchronized GPSPosition parse(String[] tokens) {
        assert tokens != null;

        return new GPSPositionBuilder()
                .addTime(Double.valueOf(tokens[1]))
                .addLatitude(Double.valueOf(tokens[2]))
                .addLongitude(Double.valueOf(tokens[4]))
                .addQuality(Double.valueOf(tokens[6]))
                .addAltitude(Double.valueOf(tokens[9]))
                .build();
    }
}
