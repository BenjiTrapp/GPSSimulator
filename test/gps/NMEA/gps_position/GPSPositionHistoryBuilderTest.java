package gps.NMEA.gps_position;

import gps.NMEA.sentences.NMEASentenceTypes;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GPSPositionHistoryBuilderTest {

    @Test
    @Tag("HappyPath")
    void shouldBuildCorrecGPSPositionHistory() {
        // given
        GPSPositionHistoryBuilder positionHistoryBuilder;

        // when
        GPSPosition secondLastPost = new GPSPosition();
        secondLastPost.setLatitude(1234.0);
        GPSPositionHistory result = new GPSPositionHistoryBuilder().setType(NMEASentenceTypes.GPGGA)
                                                        .setCurrentPosition(new GPSPosition())
                                                        .setLastPosition(new GPSPosition())
                                                        .setThirdLastPosition(new GPSPosition())
                                                        .setSecondLastPosition(secondLastPost)
                                                        .build();
        // then
        assertNotNull(result);
        assertTrue(result.getSecondLastPosition().getLatitude().equals(1234.0));
        assertEquals(NMEASentenceTypes.GPGGA, result.getSentenceType());
    }

    @Test
    @Tag("ExceptionalPath")
    void shouldThrowAssertionErrorExeption() {
        assertThrows(AssertionError.class, () -> new GPSPositionHistoryBuilder().setType(null)
                                                                                .setCurrentPosition(new GPSPosition())
                                                                                .setLastPosition(new GPSPosition())
                                                                                .setThirdLastPosition(new GPSPosition())
                                                                                .setSecondLastPosition(new GPSPosition())
                                                                                .build());

        assertThrows(AssertionError.class, () -> new GPSPositionHistoryBuilder().setType(NMEASentenceTypes.GPGGA)
                                                                                .setCurrentPosition(null)
                                                                                .setLastPosition(new GPSPosition())
                                                                                .setThirdLastPosition(new GPSPosition())
                                                                                .setSecondLastPosition(new GPSPosition())
                                                                                .build());

        assertThrows(AssertionError.class, () -> new GPSPositionHistoryBuilder().setType(NMEASentenceTypes.GPGGA)
                                                                                .setCurrentPosition(new GPSPosition())
                                                                                .setLastPosition(null)
                                                                                .setThirdLastPosition(new GPSPosition())
                                                                                .setSecondLastPosition(new GPSPosition())
                                                                                .build());

        assertThrows(AssertionError.class, () -> new GPSPositionHistoryBuilder().setType(NMEASentenceTypes.GPGGA)
                                                                                .setCurrentPosition(new GPSPosition())
                                                                                .setLastPosition(new GPSPosition())
                                                                                .setThirdLastPosition(null)
                                                                                .setSecondLastPosition(new GPSPosition())
                                                                                .build());

        assertThrows(AssertionError.class, () -> new GPSPositionHistoryBuilder().setType(NMEASentenceTypes.GPGGA)
                                                                                .setCurrentPosition(new GPSPosition())
                                                                                .setLastPosition(new GPSPosition())
                                                                                .setThirdLastPosition(new GPSPosition())
                                                                                .setSecondLastPosition(null)
                                                                                .build());
    }
}