package gps.NMEA.parser.hardening_functions;

import gps.NMEA.gps_position.GPSPosition;
import gps.NMEA.gps_position.GPSPositionHistory;

public class DebondingStrategy implements HardeningStrategy {

    @Override
    public boolean isHardened(GPSPositionHistory positionHistory) {
//        if (newPosition != null && lastPosition != null
//                && secondLastPosition != null && thirdLastPosition != null
//                && cnt.get() < 3)
//            if (newPosition.isEqual(lastPosition)
//                    && newPosition.isEqual(secondLastPosition)
//                    && newPosition.isEqual(thirdLastPosition))
//                return true;

        return false;
    }
}
