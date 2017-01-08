package gps.NMEA.parser.hardening_functions;

import gps.NMEA.gps_position.GPSPosition;
import gps.NMEA.gps_position.GPSPositionHistory;
import gps.NMEA.sentences.NMEASentenceTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StuckAtErrorDetectionStrategyTest {
    private GPSPosition current = new GPSPosition();
    private GPSPosition last = new GPSPosition();
    private GPSPosition second_last = new GPSPosition();
    private GPSPosition third_last = new GPSPosition();
    private GPSPositionHistory gpsPositionHistory;

    @BeforeEach
    public void setUp() {
        gpsPositionHistory = new GPSPositionHistory(NMEASentenceTypes.GPGGA);
        current.setAltitude(new Double("123.5"));
        current.setLongitude(new Double("456.5"));
        current.setLatitude(new Double("789.5"));

        last.setAltitude(new Double("123.4"));
        last.setLongitude(new Double("456.4"));
        last.setLatitude(new Double("789.4"));

        second_last.setAltitude(new Double("123.4"));
        second_last.setLongitude(new Double("456.4"));
        second_last.setLatitude(new Double("789.4"));

        third_last.setAltitude(new Double("123.4"));
        third_last.setLongitude(new Double("456.4"));
        third_last.setLatitude(new Double("789.4"));
    }

    @Test
    public void shouldReturnTrueWhenAllPositionsAreEqual() {
        // given
        gpsPositionHistory.addCurrentPosition(current);
        gpsPositionHistory.addLastPosition(current);
        gpsPositionHistory.addSecondLastPosition(current);
        gpsPositionHistory.addThirdLastPosition(current);

        // when
        boolean result = new StuckAtErrorDetectionStrategy().isPerturbationDetected(gpsPositionHistory);

        //then
        assertTrue(result);
    }

    @Test
    public void shouldReturnFalseWhenAllPositionsAreDifferent() {
        // given
        gpsPositionHistory.addCurrentPosition(current);
        gpsPositionHistory.addLastPosition(last);
        gpsPositionHistory.addSecondLastPosition(second_last);
        gpsPositionHistory.addThirdLastPosition(third_last);

        // when
        boolean result = new StuckAtErrorDetectionStrategy().isPerturbationDetected(gpsPositionHistory);

        //then
        assertFalse(result);
    }
}