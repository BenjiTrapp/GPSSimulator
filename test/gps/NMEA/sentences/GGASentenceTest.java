package gps.NMEA.sentences;

import gps.NMEA.utils.ChecksumUtilities;
import gps.data.GPSData;
import gps.data.GPSDataEnumHolder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GGASentenceTest {

    @AfterEach
    public void tearDown(){
        GPSData.reinitialize();
    }

    @Test
    void shouldUseDefaultSentenceWhenStatusIsV() {
        // given
        GPSData.setStatus(GPSDataEnumHolder.Status.V);
        NMEASentence ggaSentence = new GGASentence();

        // when
        String result = ggaSentence.getSentence();

        // then
        assertTrue(result.contains("$GPGGA"));
        assertTrue(result.contains(",,,,,,,,,,,,,*7A"));
    }

    @Test
    void shouldBuildValidGGASentenceWhenStatusIsA() {
        // given
        assertEquals(GPSDataEnumHolder.Status.A, GPSData.getStatus());
        NMEASentence ggaSentence = new GGASentence();

        // when
        String result = ggaSentence.getSentence();

        // then
        assertTrue(result.contains("GPGGA"));
        assertTrue(result.contains("5333.43"));
        assertTrue(result.contains("1001.39"));
        assertTrue(result.contains("8.0,"));
        assertTrue(result.contains("2.0,"));
        assertTrue(result.contains("4,"));
        assertTrue(result.contains("S"));
        assertTrue(result.contains("E"));
        assertTrue(result.contains(",M,0,M,,"));
        assertTrue(result.contains("*"));
    }

    @Test
    void shouldCreateValidGGASentence() {
        // given
        NMEASentence sentence;

        // when
        sentence = new GGASentence();

        // then
        assertNotNull(sentence);
        assertTrue(sentence instanceof  NMEASentence);
        assertTrue(sentence instanceof  GGASentence);
    }
}