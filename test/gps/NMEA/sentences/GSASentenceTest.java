package gps.NMEA.sentences;

import gps.NMEA.utils.ChecksumUtilities;
import gps.data.GPSData;
import gps.data.GPSDataEnumHolder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static java.lang.Integer.parseInt;
import static org.junit.jupiter.api.Assertions.*;

class GSASentenceTest {

    @BeforeEach
    public void setUp(){
        GPSData.reinitialize();
    }

    @AfterEach
    public void tearDown(){
        GPSData.reinitialize();
    }

    @Test
    public void shouldCalculateCorrectGSASentence() {
        // given
        GPSData.setStatus(GPSDataEnumHolder.Status.V);
        GSASentence gsaSentence = new GSASentence();

        // when
        String result = gsaSentence.getSentence();

        // then
        assertEquals("$GPGSA,,,,,,,,,,,,,,,,,*6E", result);
    }

    @Test
    public void shouldCreateCorrectGSASentenceBasedOnInitGPSValues() {
        // given
        GSASentence gsaSentence = new GSASentence();

        // when
        String result = gsaSentence.getSentence();

        // then
        System.out.println(result);
        assertTrue(result.contains("$GPGSA,A,3.0,"));
        assertTrue(result.contains(",2.8,2.0,2.4*"));
        assertTrue(ChecksumUtilities.isChecksumValid(result));
    }

    @Test
    void shouldUseDefaultSentenceWhenStatusIsV() {
        // given
        GSASentence gsaSentence = new GSASentence();
        GPSData.setSatellites("12");

        // when
        String result = gsaSentence.createSatellitePRNs();

        // then
        System.out.println(result);
    }

    @Test
    void shouldHaveAll12PlacesFilledWhen12SatellitesAreAvailable() {
        // given
        GSASentence gsaSentence = new GSASentence();
        GPSData.setSatellites("12");

        // when
        String result = gsaSentence.createSatellitePRNs();

        // then
        assertEquals("00,01,02,03,04,05,06,07,08,09,10,11,", result);
    }

    @Test
    void shouldHaveA4PlacesFilledWhen4SatellitesAreAvailable() {
        // given
        GSASentence gsaSentence = new GSASentence();
        GPSData.setSatellites("4");
        int delimiterCnt = 0;
        int digitCnt = 0;

        // when
        String result = gsaSentence.createSatellitePRNs();
        System.out.println(result);


        // then
        assertTrue(result.contains(","));

        for (int i = 0; i < result.length(); i++) {
            if (',' == result.charAt(i))
                delimiterCnt++;

            try {
                parseInt(String.valueOf(result.charAt(i)));
                digitCnt++;
            } catch (Exception ignored) {}
        }
        assertEquals(12, delimiterCnt);
        assertEquals(8, digitCnt); // must be twice, because every digit contains of two dingle digits
    }
}