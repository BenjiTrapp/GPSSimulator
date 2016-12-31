package gps.NMEA.parser.hardening_functions;

import gps.NMEA.gps_position.GPSPosition;
import gps.NMEA.gps_position.GPSPositionHistory;

public class DebondingStrategy implements HardeningStrategy {

    /**
     * This function checks based on the last three known positions if the receiver is stuck
     * @param newPosition position that shall be matched against the last three known positions
     * @return true if the receiver is stuck otherwise false
     */
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
