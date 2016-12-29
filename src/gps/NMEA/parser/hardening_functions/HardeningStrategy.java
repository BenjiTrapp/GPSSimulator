package gps.NMEA.parser.hardening_functions;

import gps.NMEA.gps_position.GPSPosition;
import gps.NMEA.gps_position.GPSPositionHistory;

public interface HardeningStrategy {
    boolean isHardened(GPSPositionHistory positionHistory);
}
