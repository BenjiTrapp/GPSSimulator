package gps.NMEA.parser.hardening_functions;

import Annotations.HardeningFunction;
import gps.NMEA.gps_position.GPSPositionHistory;

@FunctionalInterface
@HardeningFunction
public interface HardeningStrategy {
    boolean isPerturbationDetected(GPSPositionHistory positionHistory);
}
