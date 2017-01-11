package gps.NMEA.parser.hardening_functions;

import gps.NMEA.gps_position.GPSPositionHistory;

@FunctionalInterface
public interface HardeningStrategy {
    boolean isPerturbationDetected(GPSPositionHistory positionHistory);
}
