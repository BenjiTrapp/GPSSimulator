package gps.NMEA.parser.hardening_functions;

import gps.NMEA.gps_position.GPSPositionHistory;

public class SpoofingDetectionStrategy implements HardeningStrategy {
    /**
     * Function to check the received Sentence if a dash has occurred based on the data of the last position
     * @param positionHistory  New position that shall be matched against the data of the last position
     * @return true if a dash has been recognized otherwise false
     */
    @Override
    public boolean isHardened(GPSPositionHistory positionHistory) {
//        synchronized (gpsPositions)
//        {
//            if (newPosition != null && lastPosition != null)
//            {
//                if ((newPosition.getLatitude() > (lastPosition.getLatitude() + 2.5))
//                        || (newPosition.getLongitude() > lastPosition
//                        .getLongitude() + 2.5))
//                {
//                    dashed.set(true);
//                    return true;
//                }
//            }
//        }
        return false;
    }
}
