package gps.NMEA.parser.hardening_functions;

import annotations.HardeningFunction;
import gps.NMEA.gps_position.GPSPositionHistory;

@FunctionalInterface
@HardeningFunction
public interface HardeningStrategy {
    boolean isPerturbationDetected(GPSPositionHistory positionHistory);
}
