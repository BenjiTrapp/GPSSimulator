package gps.NMEA.parser.hardening_functions;

import gps.NMEA.gps_position.GPSPositionHistory;

public class StuckAtErrorDetectionStrategy implements HardeningStrategy {

    /**
     * This function checks based on the last three known positions if the receiver is stuck
     *
     * @return true if the receiver is stuck otherwise false
     */
    @Override
    public boolean isPerturbationDetected(GPSPositionHistory posHistory) {
        return      isHistoricPositionNOTNull(posHistory)
                && posHistory.getCurrentPosition().isBasicPositionEqual(posHistory.getLastPosition())
                && posHistory.getCurrentPosition().isBasicPositionEqual(posHistory.getSecondLastPosition())
                && posHistory.getCurrentPosition().isBasicPositionEqual(posHistory.getThirdLastPosition());
    }

    private boolean isHistoricPositionNOTNull(GPSPositionHistory posHistory) {
        return (posHistory.getCurrentPosition() != null
                && posHistory.getLastPosition() != null
                && posHistory.getSecondLastPosition() != null
                && posHistory.getThirdLastPosition() != null);
    }
}
