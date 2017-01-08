package gps.NMEA.parser.hardening_functions;

import gps.NMEA.gps_position.GPSPositionHistory;

public class SpoofingDetectionStrategy implements HardeningStrategy {

    private static double CONFIDENCE_INTERVAL;

    public SpoofingDetectionStrategy() {CONFIDENCE_INTERVAL = 5;} // 2.5

    public SpoofingDetectionStrategy(double confidenceInterval) {CONFIDENCE_INTERVAL = confidenceInterval;}

    /**
     * Function to check the received Sentence if a dash has occurred based on the data of the last position
     *
     * @param positionHistory New position that shall be matched against the data of the last position
     * @return true if a dash has been recognized otherwise false
     */
    @Override
    public boolean isPerturbationDetected(GPSPositionHistory positionHistory) {
        return     (positionHistory.getCurrentPosition() != null && positionHistory.getLastPosition() != null) &&
                 (positionHistory.getCurrentPosition().getLatitude()  > (positionHistory.getLastPosition().getLatitude() + CONFIDENCE_INTERVAL)
                || positionHistory.getCurrentPosition().getLongitude() > (positionHistory.getLastPosition().getLongitude() + CONFIDENCE_INTERVAL)
                || positionHistory.getCurrentPosition().getAltitude()  > (positionHistory.getLastPosition().getAltitude() + CONFIDENCE_INTERVAL));
    }
}