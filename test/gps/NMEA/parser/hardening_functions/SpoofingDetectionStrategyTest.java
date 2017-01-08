package gps.NMEA.parser.hardening_functions;

import gps.NMEA.gps_position.GPSPosition;
import gps.NMEA.gps_position.GPSPositionHistory;
import gps.NMEA.sentences.NMEASentenceTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SpoofingDetectionStrategyTest {
    private GPSPosition position1 = new GPSPosition();
    private GPSPosition position2 = new GPSPosition();
    private GPSPositionHistory gpsPositionHistory;
    private static final double SPOOFING_COORD_DASH = 42.0;

    @BeforeEach
    public void setUp() {
        gpsPositionHistory = new GPSPositionHistory(NMEASentenceTypes.GPGGA);
        position1.setAltitude(new Double("123.0"));
        position1.setLongitude(new Double("456.0"));
        position1.setLatitude(new Double("789"));

        position2.setAltitude(new Double("123" + SPOOFING_COORD_DASH));
        position2.setLongitude(new Double("456" + SPOOFING_COORD_DASH));
        position2.setLatitude(new Double("789" + SPOOFING_COORD_DASH));
    }

    @Test
    public void shouldThrowAssertErrorWhenCurrentPositionIsNull() {
        // given
        gpsPositionHistory.addLastPosition(position2);

        // when
        Throwable exception = assertThrows(AssertionError.class, () -> gpsPositionHistory.addCurrentPosition(null));

        //then
        assertEquals(AssertionError.class, exception.getClass());
    }

    @Test
    public void shouldThrowAssertErrorWhenLastPositionIsNull() {
        // given
        gpsPositionHistory.addCurrentPosition(position2);

        // when
        Throwable exception = assertThrows(AssertionError.class, () -> gpsPositionHistory.addLastPosition(null));

        //then
        assertEquals(AssertionError.class, exception.getClass());
    }

    @Test
    public void shouldReturnTrueWhenPositionIsSpoofedUpperBoundary() {
        // given
        gpsPositionHistory.addCurrentPosition(position2);
        gpsPositionHistory.addLastPosition(position1);

        // when
        boolean result = new SpoofingDetectionStrategy().isPerturbationDetected(gpsPositionHistory);

        //then
        assertTrue(result);
    }

    @Test
    public void shouldReturnFalseWhenPositionIsNOTSpoofed() {
        // given
        String tiny_progress = ".5";
        position2.setAltitude(new Double("123" + tiny_progress));
        position2.setLongitude(new Double("456" + tiny_progress));
        position2.setLatitude(new Double("789" + tiny_progress));

        System.out.println(position2.getAltitude());
        System.out.println(position2.getLongitude());
        System.out.println(position2.getLatitude());

        gpsPositionHistory.addCurrentPosition(position1);
        gpsPositionHistory.addLastPosition(position2);

        // when
        boolean result = new SpoofingDetectionStrategy().isPerturbationDetected(gpsPositionHistory);

        //then
        assertFalse(result);
    }
}