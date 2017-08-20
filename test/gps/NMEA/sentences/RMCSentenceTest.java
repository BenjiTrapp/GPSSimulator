package gps.NMEA.sentences;

import gps.data.GPSData;
import gps.data.GPSDataEnumHolder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RMCSentenceTest {
    @AfterEach
    void tearDown(){
        GPSData.reinitialize();
    }

    @Test
    void shouldUseDefaultSentenceWhenStatusIsV() {
        // given
        GPSData.setStatus(GPSDataEnumHolder.Status.V);
        NMEASentence ggaSentence = new RMCSentence();

        // when
        String result = ggaSentence.getSentence();

        // then
        assertTrue(result.contains("GPRMC"));
        assertTrue(result.contains(",V,,,,,,,,,,N*31"));
    }

    @Test
    void shouldBuildValidGGASentenceWhenStatusIsA() {
        // given
        assertEquals(GPSDataEnumHolder.Status.A, GPSData.getStatus());
        NMEASentence rmcSentence = new RMCSentence();

        // when
        String result = rmcSentence.getSentence();
        System.out.println(result);

        // then
        assertTrue(result.contains("GPRMC"));
        assertTrue(result.contains("5333.43"));
        assertTrue(result.contains("1001.39"));
        assertTrue(result.contains("A,"));
        assertTrue(result.contains("003.0,"));
        assertTrue(result.contains("314.0"));
        assertTrue(result.contains(rmcSentence.getDatetime()));
        assertTrue(result.contains("S"));
        assertTrue(result.contains("E"));
        assertTrue(result.contains(",,S"));
        assertTrue(result.contains("*"));
    }

    @Test
    void shouldCreateValidGGASentence() {
        // given
        NMEASentence sentence;

        // when
        sentence = new RMCSentence();

        // then
        assertNotNull(sentence);
        assertTrue(sentence instanceof  NMEASentence);
        assertTrue(sentence instanceof  RMCSentence);
    }
}